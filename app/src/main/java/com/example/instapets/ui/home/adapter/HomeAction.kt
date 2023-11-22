package com.example.instapets.ui.home.adapter

import androidx.appcompat.widget.AppCompatImageButton
import com.example.instapets.domain.model.home.HomePetModel

sealed interface HomeAction {
    data class OnPetLiked(val homePet: HomePetModel, val state:Boolean) : HomeAction

    data class OnPetSaved(val homePet: HomePetModel) : HomeAction

    data class OnMoreOptions(val homePet: HomePetModel, val button: AppCompatImageButton) :
        HomeAction

    data class SeePetDescription(val petId: String) : HomeAction
}