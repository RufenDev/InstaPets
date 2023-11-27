package com.example.instapets.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instapets.core.PetTypes
import com.example.instapets.domain.BreedOrCategoryFilter
import com.example.instapets.domain.model.filter.FilterModel
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.domain.usecase.GetPetByFilter
import com.example.instapets.ui.core.preferences.ConfigurationPreferences
import com.example.instapets.ui.core.states.States
import com.example.instapets.ui.core.states.States.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val configurations: ConfigurationPreferences,
    private val getPetsByFilter: GetPetByFilter,
) : ViewModel() {

    private val _state = MutableStateFlow(REFRESH)
    val state: StateFlow<States> = _state

    private val _pets = MutableStateFlow<List<SearchPetModel>>(emptyList())
    val pets: StateFlow<List<SearchPetModel>> = _pets

    private var _petPreference: PetTypes
    val petPreference get() = _petPreference

    private var isRefreshing: Boolean = false
    private var cancel = false

    var filter: FilterModel = FilterModel(id = "", petType = true)
    var filterType: BreedOrCategoryFilter = true

    init {
        _petPreference = configurations.loadPet()
        viewModelScope.launch {
            launch {
                configurations.petFlow.collect {
                    Log.i("ññ", "SearchViewModel PetCollect -> ${it.name}")
                    _petPreference = it
                    filter = FilterModel(id = "", petType = true)
                    filterType = true
                    refreshSearch()
                }
            }
            launch { refreshSearch() }
        }
    }

    suspend fun refreshSearch() {
        if (!isRefreshing) {
            isRefreshing = true
            cancel = false

            _state.value = REFRESH
            _pets.value = emptyList()

            delay(100)

            getPets()
        }
    }

    suspend fun getPets(
        lastPosition: Int = 0,
    ) {
        val isNotLoadingMorePets = _state.value != LOAD_MORE
        val lastItemIsVisible = lastPosition >= _pets.value.size.minus(6)

        if (lastItemIsVisible && isNotLoadingMorePets && !cancel) {
            _state.value = LOAD_MORE

            val response = getPetsByFilter(filter, filterType, _petPreference)

            if (response.isNotEmpty()) {
                val oldList = _pets.value
                val newList = oldList.plus(response).distinct()

                cancel = oldList == newList

                if (_state.value == LOAD_MORE && !cancel) {
                    _pets.value = newList
                    _state.value = SUCCESS
                }

            } else if (_pets.value.isNotEmpty() && _state.value == LOAD_MORE) {
                _pets.value = _pets.value

            } else {
                _state.value = ERROR
            }
        }
        isRefreshing = false
    }
}