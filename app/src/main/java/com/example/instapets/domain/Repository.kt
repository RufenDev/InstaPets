package com.example.instapets.domain

import com.example.instapets.core.PetTypes
import com.example.instapets.domain.model.CategoryModel
import com.example.instapets.domain.model.SimpleBreedModel
import com.example.instapets.domain.model.description.DescriptionPetModel
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.model.search.SearchPetModel

interface Repository {
    suspend fun getPetsImages(type: PetTypes): List<HomePetModel>

    suspend fun getPetDescription(id: String, type: PetTypes): DescriptionPetModel

    suspend fun getBreedsList(type: PetTypes): List<SimpleBreedModel>

    suspend fun getCategoriesList(): List<CategoryModel>

    suspend fun getPetsByFilter(breedId:String, categoryId:String, type:PetTypes) : List<SearchPetModel>
}