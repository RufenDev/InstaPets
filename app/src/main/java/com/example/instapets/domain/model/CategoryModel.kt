package com.example.instapets.domain.model

import com.example.instapets.data.network.response.CategoryItem

data class CategoryModel(
    val id: String,
    val name: String
){
    companion object{

        fun CategoryItem.CategoriesResponse.toDomain() = map{ CategoryModel(it.id, it.name) }

    }
}

