package com.example.instapets.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instapets.core.PetTypes
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.usecase.GetPetImages
import com.example.instapets.ui.home.viewmodel.HomeState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getPetImages: GetPetImages) : ViewModel() {

    private val _state = MutableStateFlow(HOME_REFRESH)
    val state: StateFlow<HomeState> = _state

    private val _pets = MutableStateFlow<List<HomePetModel>>(emptyList())
    val pets: StateFlow<List<HomePetModel>> = _pets

    init {
        viewModelScope.launch {
            getRandomPets()
        }
    }

    suspend fun getRandomPets(lastPosition: Int = 0, refreshing: Boolean = false) {
        if (refreshing) {
            _state.value = HOME_REFRESH
            _pets.value = emptyList()
            delay(100)
        }

        val isLastPositionVisible = lastPosition >= _pets.value.size.minus(1)
        val isNotLoadingMorePets = _state.value != HOME_LOAD_MORE

        if (isLastPositionVisible && isNotLoadingMorePets) {
            _state.value = HOME_LOAD_MORE

            val response = getPetImages(PetTypes.CAT)

            if (response.isNotEmpty()) {
                _pets.value = _pets.value.minus(loadingBar).plus(response).plus(loadingBar)
                _state.value = HOME_SUCCESS

            } else if (_pets.value.isEmpty()) {
                _state.value = HOME_ERROR

            } else {
                _pets.value = _pets.value.minus(loadingBar)
            }
        }
    }

    companion object {
        val loadingBar = HomePetModel("loading_bar", "", emptyList(), emptyList())
        const val LOADING_TYPE = -1
    }

}