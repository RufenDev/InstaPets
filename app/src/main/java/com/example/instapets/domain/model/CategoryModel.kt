package com.example.instapets.domain.model

import com.example.instapets.core.CoreExtensions.titleCase
import com.example.instapets.data.network.response.CategoryItem

data class CategoryModel(
    val id: String,
    val name: String,
) {
    companion object {
        fun CategoryItem.CategoriesResponse.toCategoryModel() = map {
            CategoryModel(it.id.titleCase(), it.name)
        }.sortedBy { it.name }
    }
}

