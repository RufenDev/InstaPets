package com.example.instapets.data

import com.example.instapets.core.PetTypes
import com.example.instapets.core.PetTypes.CAT
import com.example.instapets.core.PetTypes.DOG
import com.example.instapets.core.PetTypes.PETS
import com.example.instapets.data.network.APIClient
import com.example.instapets.domain.Repository
import com.example.instapets.domain.BreedOrCategoryFilter
import com.example.instapets.domain.model.description.DescriptionPetModel
import com.example.instapets.domain.model.description.DescriptionPetModel.Companion.toDescriptionModel
import com.example.instapets.domain.model.filter.FilterModel
import com.example.instapets.domain.model.filter.FilterModel.Companion.toFilterModel
import com.example.instapets.domain.model.filter.IsACat
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.model.search.SearchPetModel
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(private val apiClient: APIClient) : Repository {

    override suspend fun getPetsImages(type: PetTypes): List<HomePetModel> {
        val response = when (type) {
            CAT -> apiClient.cat.getCatImagesFromAPI()
            DOG -> apiClient.dog.getDogImagesFromAPI()
            PETS -> apiClient.pets.getPetImagesFromAPI()
        }

        response?.let {
            return it

        } ?: run {
            //Return from DATABASE
            return emptyList()
        }
    }

    override suspend fun getPetDescription(id: String, type: IsACat): DescriptionPetModel {
        val response =
            if (type) apiClient.cat.getCatDescriptionFromAPI(id)
            else apiClient.dog.getDogDescriptionFromAPI(id)

        response?.let {
            return it.toDescriptionModel()

        } ?: run {
            //Return from database
            return DescriptionPetModel("", "", emptyList(), emptyList())
        }
    }

    override suspend fun getPetFilters(filter: BreedOrCategoryFilter, type: PetTypes) =
        if (filter) {
            getBreedsList(type)

        } else if (type != DOG) {
            getCategoriesList()

        } else {
            emptyList()
        }

    private suspend fun getBreedsList(type: PetTypes): List<FilterModel> {
        val response = when (type) {
            CAT -> apiClient.cat.getCatBreedsFromAPI()
            DOG -> apiClient.dog.getDogBreedsFromAPI()
            PETS -> apiClient.pets.getPetBreedsFromAPI()
        }

        response?.let {
            return it.toFilterModel()

        } ?: run {
            //Return from Database
            return emptyList()
        }
    }

    private suspend fun getCategoriesList(): List<FilterModel> {
        apiClient.cat.getCatCategoriesFromAPI()?.let {
            return it.toFilterModel()

        } ?: run {
            //Return from Database
            return emptyList()
        }
    }

    override suspend fun getPetsByFilter(
        filter: FilterModel, filterType: BreedOrCategoryFilter, petPreference:PetTypes
    ): List<SearchPetModel> {
        val response = when (petPreference) {
            CAT -> apiClient.cat.getCatsByFilters(filter.id, filterType)
            DOG -> apiClient.dog.getDogsByFilters(filter.id, filterType)
            PETS -> apiClient.pets.getPetsByFilters(filter, filterType)
        }

        response?.let {
            return it

        } ?: run {
            //return from database
            return emptyList()
        }
    }
}