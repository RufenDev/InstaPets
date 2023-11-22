package com.example.instapets.ui.home

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

    private val _swipe = MutableStateFlow(false)
    val swipe: StateFlow<Boolean> = _swipe

    private var petPreference: PetTypes

    init {
        petPreference = configurations.loadPet()
        viewModelScope.launch {
            launch {
                configurations.petFlow.collect {
                    petPreference = it
                    refreshingHome()
                }
            }
            launch { getRandomPets() }
        }
    }

    suspend fun refreshingHome() {
        _state.value = REFRESH
        _pets.value = emptyList()

        delay(100)

        _swipe.value = false
        getRandomPets()
    }

    suspend fun getRandomPets(lastPosition: Int = 0) {
        val isNotLoadingMorePets = _state.value != LOAD_MORE
        val lastItemIsVisible = lastPosition >= _pets.value.size.minus(2)

        if (lastItemIsVisible && isNotLoadingMorePets) {
            _state.value = LOAD_MORE

            val response = petImages(petPreference)

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
        _swipe.value = true
    }

    fun petLiked(pet: HomePetModel) {

    }

    fun petSaved(pet: HomePetModel) {

    }

    private companion object {
        val loadingBar = HomePetModel(LOADING_BAR_ID, "", emptyList(), emptyList())
    }

}