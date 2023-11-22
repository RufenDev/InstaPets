package com.example.instapets.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instapets.core.PetTypes
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.domain.usecase.GetPetByFilter
import com.example.instapets.ui.core.preferences.ConfigurationPreferences
import com.example.instapets.ui.core.states.States
import com.example.instapets.ui.core.states.States.*
import com.example.instapets.ui.core.views.LoadingBarViewHolder.Companion.LOADING_BAR_ID
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

    private var petPreference: PetTypes

    var selectedBreedId: String = ""
    var selectedCategoryId: String = ""

    init {
        petPreference = configurations.loadPet()
        viewModelScope.launch {
            launch {
                configurations.petFlow.collect {
                    petPreference = it
                    refreshSearch()
                }
            }
            launch {
                refreshSearch()
            }
        }
    }

    suspend fun refreshSearch() {
        _state.value = REFRESH
        _pets.value = emptyList()
        delay(100)
        getPets()
    }

    suspend fun getPets(
        lastPosition: Int = 0
    ) {
        val isNotLoadingMorePets = _state.value != LOAD_MORE
        val lastItemIsVisible = lastPosition >= _pets.value.size.minus(3)

        if (lastItemIsVisible && isNotLoadingMorePets) {
            _state.value = LOAD_MORE

            val response = getPetsByFilter(selectedBreedId, selectedCategoryId, petPreference)

            if (response.isNotEmpty()) {
                val oldList = _pets.value.minus(loadingBar)
                val newList = oldList.plus(response).plus(loadingBar)

                if(_state.value == LOAD_MORE){
                    _pets.value = newList
                    _state.value = SUCCESS
                }

            } else if (_pets.value.isNotEmpty() && _state.value == LOAD_MORE) {
                _pets.value = _pets.value.minus(loadingBar)

            } else {
                _state.value = ERROR
            }
        }
    }

    private companion object {
        val loadingBar = SearchPetModel(LOADING_BAR_ID, "")
    }
}