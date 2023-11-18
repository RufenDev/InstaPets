package com.example.instapets.data

import com.example.instapets.core.PetTypes
import com.example.instapets.core.PetTypes.CAT
import com.example.instapets.core.PetTypes.DOG
import com.example.instapets.core.PetTypes.PETS
import com.example.instapets.data.network.APIClient
import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.Repository
import com.example.instapets.domain.model.CategoryModel
import com.example.instapets.domain.model.CategoryModel.Companion.toDomain
import com.example.instapets.domain.model.description.PetDescriptionModel
import com.example.instapets.domain.model.description.PetDescriptionModel.Companion.toDomain
import com.example.instapets.domain.model.home.HomeBreedModel
import com.example.instapets.domain.model.home.HomeBreedModel.Companion.toDomain
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.model.home.HomePetModel.Companion.toDomain
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(private val apiClient: APIClient) : Repository {

    override suspend fun getPetImages(type: PetTypes): List<HomePetModel> {
        val response: PetItem.PetResponse? = when (type) {
            CAT -> apiClient.cat.getCatImagesFromAPI()
            DOG -> apiClient.dog.getDogImagesFromAPI()
            PETS -> apiClient.pets.getPetImagesFromAPI()
        }

        response?.let {
            return it.toDomain()

        } ?: run {
            //Return from DATABASE
            return emptyList()
        }
    }

    override suspend fun getPetDescription(id: String, type: PetTypes): PetDescriptionModel {
        val response = when (type) {
            CAT -> apiClient.cat.getCatDescriptionFromAPI(id)
            DOG -> apiClient.dog.getDogDescriptionFromAPI(id)
            else -> null
        }

        response?.let {
            return it.toDomain()

        } ?: run {
            //Return from database
            return PetDescriptionModel("", "", emptyList(), emptyList())
        }
    }

    override suspend fun getPetBreedsList(type: PetTypes): List<HomeBreedModel> {
        val response = when (type) {
            CAT -> apiClient.cat.getCatBreedsFromAPI()
            DOG -> apiClient.dog.getDogBreedsFromAPI()
            PETS -> apiClient.pets.getPetBreedsFromAPI()
        }

        response?.let {
            return it.toDomain()
        } ?: run {
            //Return from Database
            return emptyList()
        }
    }

    override suspend fun getCatCategoriesList(): List<CategoryModel> {
        apiClient.cat.getCatCategoriesFromAPI()?.let {
            return it.toDomain()

        } ?: run {
            //Return from Database
            return emptyList()
        }
    }
}