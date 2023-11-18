package com.example.instapets.domain.model.home

import com.example.instapets.core.PetTypes
import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.CategoryModel
import kotlin.random.Random

data class HomePetModel(
    val id: String,
    val image: String,
    val breeds: List<HomeBreedModel>,
    val categories: List<CategoryModel>,
    var isPetLiked: Boolean = false,
    var isPetSaved: Boolean = false
) {
    companion object {
        fun PetItem.PetResponse.toDomain() = map { pet ->
            HomePetModel(
                id = pet.id,
                image = pet.url,
                categories = pet.categories.map { CategoryModel(it.id, it.name) },
                breeds = pet.breeds.map { breed ->
                    HomeBreedModel(
                        breed.id,
                        breed.name,
                        if (breed.height == null) PetTypes.CAT else PetTypes.DOG
                    )
                },
                isPetLiked = Random.nextBoolean(),
                isPetSaved = Random.nextBoolean()
            )
        }
    }
}