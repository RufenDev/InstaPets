package com.example.instapets.domain.model.home

import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.filter.FilterModel

data class HomePetModel(
    val id: String,
    val image: String,
    val breeds: List<FilterModel>,
    val categories: List<FilterModel>,
    var isPetLiked: Boolean = false,
    var isPetSaved: Boolean = false,
) {
    companion object {
        fun PetItem.PetResponse.toHomeModel() = map { pet ->
            HomePetModel(
                id = pet.id,
                image = pet.url,
                categories = pet.categories.map { FilterModel(it.id, it.name, true) },
                breeds = pet.breeds.map { breed ->
                    FilterModel(
                        breed.id,
                        breed.name,
                        breed.height == null
                    )
                }
            )
        }.shuffled()
    }
}