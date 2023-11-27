package com.example.instapets.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instapets.core.PetTypes
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.usecase.GetPetImages
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
class HomeViewModel @Inject constructor(
    private val petImages: GetPetImages,
    private val configurations: ConfigurationPreferences,
) : ViewModel() {

    private val _state = MutableStateFlow(REFRESH)
    val state: StateFlow<States> = _state

    private val _pets = MutableStateFlow<List<HomePetModel>>(emptyList())
    val pets: StateFlow<List<HomePetModel>> = _pets

    private var isRefreshing: Boolean = false
    private var cancel:Boolean = false
    private var petPreference: PetTypes

    init {
        petPreference = configurations.loadPet()
        viewModelScope.launch {
            launch {
                configurations.petFlow.collect {
                    petPreference = it
                    Log.i("ññ", "HomeViewModel PetCollect -> ${it.name}")
                    refreshingHome()
                }
            }
            launch { getRandomPets() }
        }
    }

    suspend fun refreshingHome() {
        if(!isRefreshing){
            isRefreshing = true
            cancel = false

            _state.value = REFRESH
            _pets.value = emptyList()

            delay(100)

            getRandomPets()
        }
    }

    suspend fun getRandomPets(lastPosition: Int = 0) {
        val isNotLoadingMorePets = _state.value != LOAD_MORE
        val lastItemIsVisible = lastPosition >= _pets.value.size.minus(2)

        if (lastItemIsVisible && isNotLoadingMorePets && !cancel) {
            _state.value = LOAD_MORE

            val response = petImages(petPreference)

            if (response.isNotEmpty()) {
                val oldList = _pets.value.minus(loadingBar)
                val newList = oldList.plus(response).distinct()

                cancel = oldList == newList

                if(_state.value == LOAD_MORE && !cancel){
                    _pets.value = newList.plus(loadingBar)
                    _state.value = SUCCESS
                }

            } else if (_pets.value.isNotEmpty() && _state.value == LOAD_MORE) {
                _pets.value = _pets.value.minus(loadingBar)

            } else {
                _state.value = ERROR
            }

        }
        isRefreshing = false
    }

    fun petLiked(pet: HomePetModel) {

    }

    fun petSaved(pet: HomePetModel) {

    }

    private companion object {
        val loadingBar = HomePetModel(LOADING_BAR_ID, "", emptyList(), emptyList(), false)
    }

}