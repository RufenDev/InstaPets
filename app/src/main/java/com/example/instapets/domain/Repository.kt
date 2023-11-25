package com.example.instapets.domain

import com.example.instapets.core.PetTypes
import com.example.instapets.domain.model.description.DescriptionPetModel
import com.example.instapets.domain.model.filter.FilterModel
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.model.search.SearchPetModel

typealias BreedOrCategoryFilter = Boolean

interface Repository {

    suspend fun getPetsImages(type: PetTypes): List<HomePetModel>

    suspend fun getPetDescription(id: String, type: PetTypes): DescriptionPetModel

    suspend fun getPetFilters(filter: BreedOrCategoryFilter, type: PetTypes): List<FilterModel>

    suspend fun getPetsByFilter(
        filter: FilterModel,
        filterType: BreedOrCategoryFilter,
        petPreference: PetTypes,
    ): List<SearchPetModel>
}