package com.example.instapets.data

import com.example.instapets.core.PetTypes
import com.example.instapets.core.PetTypes.CAT
import com.example.instapets.core.PetTypes.DOG
import com.example.instapets.core.PetTypes.PETS
import com.example.instapets.data.network.APIClient
import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.Repository
import com.example.instapets.domain.model.CategoryModel
import com.example.instapets.domain.model.CategoryModel.Companion.toCategoryModel
import com.example.instapets.domain.model.SimpleBreedModel
import com.example.instapets.domain.model.SimpleBreedModel.Companion.toBreedModel
import com.example.instapets.domain.model.description.DescriptionPetModel
import com.example.instapets.domain.model.description.DescriptionPetModel.Companion.toDescriptionModel
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.model.home.HomePetModel.Companion.toHomeModel
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.domain.model.search.SearchPetModel.Companion.toSearchModel
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(private val apiClient: APIClient) : Repository {

    override suspend fun getPetsImages(type: PetTypes): List<HomePetModel> {
        val response: PetItem.PetResponse? = when (type) {
            CAT -> apiClient.cat.getCatImagesFromAPI()
            DOG -> apiClient.dog.getDogImagesFromAPI()
            PETS -> apiClient.pets.getPetImagesFromAPI()
        }

        response?.let {
            return it.toHomeModel()

        } ?: run {
            //Return from DATABASE
            return emptyList()
        }
    }

    override suspend fun getPetDescription(id: String, type: PetTypes): DescriptionPetModel {
        val response = when (type) {
            CAT -> apiClient.cat.getCatDescriptionFromAPI(id)
            DOG -> apiClient.dog.getDogDescriptionFromAPI(id)
            else -> null
        }

        response?.let {
            return it.toDescriptionModel()

        } ?: run {
            //Return from database
            return DescriptionPetModel("", "", emptyList(), emptyList())
        }
    }

    override suspend fun getBreedsList(type: PetTypes): List<SimpleBreedModel> {
        val response = when (type) {
            CAT -> apiClient.cat.getCatBreedsFromAPI()
            DOG -> apiClient.dog.getDogBreedsFromAPI()
            PETS -> apiClient.pets.getPetBreedsFromAPI()
        }

        response?.let {
            return it.toBreedModel()

        } ?: run {
            //Return from Database
            return emptyList()
        }
    }

    override suspend fun getCategoriesList(): List<CategoryModel> {
        apiClient.cat.getCatCategoriesFromAPI()?.let {
            return it.toCategoryModel()

        } ?: run {
            //Return from Database
            return emptyList()
        }
    }

    override suspend fun getPetsByFilter(
        breedId: String,
        categoryId: String,
        type: PetTypes,
    ): List<SearchPetModel> {
        val response: PetItem.PetResponse?  = when(type){
            CAT -> {apiClient.cat.getCatsByFilters(breedId, categoryId)}
            DOG -> {apiClient.dog.getDogsByFilters(breedId, categoryId)}
            PETS -> {apiClient.pets.getPetsByFilters(breedId, categoryId)}
        }

        response?.let {
            return it.toSearchModel()

        } ?: run {
            //return from database
            return emptyList()
        }
    }
}