package com.example.instapets.domain

import com.example.instapets.core.PetTypes
import com.example.instapets.domain.model.CategoryModel
import com.example.instapets.domain.model.description.PetDescriptionModel
import com.example.instapets.domain.model.home.HomeBreedModel
import com.example.instapets.domain.model.home.HomePetModel

interface Repository {
    suspend fun getPetImages(type: PetTypes): List<HomePetModel>
    suspend fun getPetDescription(id: String, type: PetTypes): PetDescriptionModel
    suspend fun getPetBreedsList(type: PetTypes): List<HomeBreedModel>
    suspend fun getCatCategoriesList(): List<CategoryModel>
}