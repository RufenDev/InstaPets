package com.example.instapets.domain.model.home

import com.example.instapets.core.PetTypes
import com.example.instapets.data.network.response.BreedItem

data class HomeBreedModel(
    val id: String,
    val name: String,
    val petType: PetTypes
) {
    companion object {
        fun BreedItem.BreedsResponse.toDomain() =
            map {
                HomeBreedModel(
                    it.id,
                    it.name,
                    if (it.height == null) PetTypes.CAT else PetTypes.DOG
                )

            }.sortedBy { it.name }
    }
}

