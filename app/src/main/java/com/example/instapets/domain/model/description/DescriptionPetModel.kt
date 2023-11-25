package com.example.instapets.domain.model.description

import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.description.DescriptionBreedModel.Companion.catBreeds
import com.example.instapets.domain.model.description.DescriptionBreedModel.Companion.dogBreeds
import com.example.instapets.domain.model.filter.FilterModel

data class DescriptionPetModel(
    val id: String,
    val image: String,
    val breeds: List<DescriptionBreedModel>,
    val categories: List<FilterModel>,
    var isPetLiked: Boolean = false,
    var isPetSaved: Boolean = false
) {
    companion object {
        fun PetItem.toDescriptionModel(): DescriptionPetModel {

            val mappedCategories = categories.map { FilterModel(it.id, it.name, true) }

            val thePetIsADog = breeds.any { it.height != null }

            val mappedBreeds: List<DescriptionBreedModel> =
                if (thePetIsADog) dogBreeds()
                else catBreeds()

            return DescriptionPetModel(id, url, mappedBreeds, mappedCategories)
        }
    }
}

