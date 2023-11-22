package com.example.instapets.domain.model

import com.example.instapets.core.CoreExtensions.titleCase
import com.example.instapets.data.network.response.BreedItem

typealias IsACat = Boolean

data class SimpleBreedModel(
    val id: String,
    val name: String,
    val petType: IsACat,
) {
    companion object {
        fun BreedItem.BreedsResponse.toBreedModel() = map {
            SimpleBreedModel(it.id, it.name.titleCase(), it.height == null)
        }.sortedBy { it.name }
    }
}

