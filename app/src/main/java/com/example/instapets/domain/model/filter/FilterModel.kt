package com.example.instapets.domain.model.filter

import com.example.instapets.core.CoreExtensions.titleCase
import com.example.instapets.data.network.response.BreedItem
import com.example.instapets.data.network.response.CategoryItem

typealias IsACat = Boolean

data class FilterModel(
    val id: String, val title: String = "", val petType: IsACat,
) {
    companion object {
        fun BreedItem.BreedsResponse.toFilterModel() = map {
            FilterModel(it.id, it.name.titleCase(), it.height == null)
        }.sortedBy { it.title }

        fun CategoryItem.CategoriesResponse.toFilterModel() = map {
            FilterModel(it.id, it.name.titleCase(), true)
        }.sortedBy { it.title }
    }
}