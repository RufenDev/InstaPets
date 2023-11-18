package com.example.instapets.domain.model.description

import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.CategoryModel
import com.example.instapets.domain.model.description.BreedDescriptionModel.Companion.catBreeds
import com.example.instapets.domain.model.description.BreedDescriptionModel.Companion.dogBreeds

data class PetDescriptionModel(
    val id: String,
    val image: String,
    val breeds: List<BreedDescriptionModel>,
    val categories: List<CategoryModel>
) {
    companion object {
        fun PetItem.toDomain(): PetDescriptionModel {

            val mappedCategories = categories.map { CategoryModel(it.id, it.name) }

            val thePetIsADog = breeds.any { it.height != null }

            val mappedBreeds: List<BreedDescriptionModel> =
                if (thePetIsADog) dogBreeds()
                else catBreeds()

            return PetDescriptionModel(id, url, mappedBreeds, mappedCategories)
        }
    }
}

