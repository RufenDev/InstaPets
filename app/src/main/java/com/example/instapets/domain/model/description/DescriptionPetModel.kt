package com.example.instapets.domain.model.description

import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.CategoryModel
import com.example.instapets.domain.model.description.DescriptionBreedModel.Companion.catBreeds
import com.example.instapets.domain.model.description.DescriptionBreedModel.Companion.dogBreeds

data class DescriptionPetModel(
    val id: String,
    val image: String,
    val breeds: List<DescriptionBreedModel>,
    val categories: List<CategoryModel>,
    var isPetLiked: Boolean = false,
    var isPetSaved: Boolean = false
) {
    companion object {
        fun PetItem.toDescriptionModel(): DescriptionPetModel {

            val mappedCategories = categories.map { CategoryModel(it.id, it.name) }

            val thePetIsADog = breeds.any { it.height != null }

            val mappedBreeds: List<DescriptionBreedModel> =
                if (thePetIsADog) dogBreeds()
                else catBreeds()

            return DescriptionPetModel(id, url, mappedBreeds, mappedCategories)
        }
    }
}

