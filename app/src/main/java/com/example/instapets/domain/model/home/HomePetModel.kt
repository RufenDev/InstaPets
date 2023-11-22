package com.example.instapets.domain.model.home

import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.CategoryModel
import com.example.instapets.domain.model.SimpleBreedModel

data class HomePetModel(
    val id: String,
    val image: String,
    val breeds: List<SimpleBreedModel>,
    val categories: List<CategoryModel>,
    var isPetLiked: Boolean = false,
    var isPetSaved: Boolean = false
) {
    companion object {
        fun PetItem.PetResponse.toHomeModel() = map { pet ->
            HomePetModel(
                id = pet.id,
                image = pet.url,
                categories = pet.categories.map { CategoryModel(it.id, it.name) },
                breeds = pet.breeds.map { breed ->
                    SimpleBreedModel(
                        breed.id,
                        breed.name,
                        breed.height == null
                    )
                }
            )
        }.shuffled()
    }
}