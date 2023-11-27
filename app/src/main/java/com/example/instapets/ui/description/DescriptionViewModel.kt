package com.example.instapets.ui.description

import androidx.lifecycle.ViewModel
import com.example.instapets.domain.model.description.DescriptionPetModel
import com.example.instapets.domain.model.filter.IsACat
import com.example.instapets.domain.usecase.GetPetDescription
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(private val getPetDescription: GetPetDescription) : ViewModel() {


    suspend fun getDescription(petId: String, type: IsACat): DescriptionPetModel {
        val id = petId.ifBlank { "yZE2JpeXz" }
        return getPetDescription(id, type)
    }

}