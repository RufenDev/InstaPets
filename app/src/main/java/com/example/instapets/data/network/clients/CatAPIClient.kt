package com.example.instapets.data.network.clients

import android.util.Log
import com.example.instapets.data.network.NetworkModule.CAT_SERVICE_NAME
import com.example.instapets.data.network.APIService
import com.example.instapets.data.network.APIService.Companion.DEFAULT_PET_COUNT
import com.example.instapets.data.network.APIService.Companion.SEARCH_PET_COUNT
import com.example.instapets.data.network.response.BreedItem.BreedsResponse
import com.example.instapets.data.network.response.CategoryItem.CategoriesResponse
import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.BreedOrCategoryFilter
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.model.home.HomePetModel.Companion.toHomeModel
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.domain.model.search.SearchPetModel.Companion.toSearchModel
import javax.inject.Inject
import javax.inject.Named

class CatAPIClient @Inject constructor(
    @Named(CAT_SERVICE_NAME) private val cat: APIService,
) {

    suspend fun getCatImagesFromAPI(): List<HomePetModel>? {
        runCatching { cat.getPetImages(DEFAULT_PET_COUNT) }
            .onFailure { Log.e("ññ", "CatImagesAPI ERROR MSJ: ${it.message}") }
            .onSuccess { return it.body()?.toHomeModel(true) ?: emptyList() }
        return null
    }

    suspend fun getCatDescriptionFromAPI(id: String): PetItem? {
        runCatching { cat.getPetDescription(id) }
            .onSuccess { return it.body() ?: PetItem() }
            .onFailure { Log.e("ññ", "CatDescriptionAPI ERROR MSJ: ${it.message}") }
        return null
    }

    suspend fun getCatBreedsFromAPI(): BreedsResponse? {
        runCatching { cat.getPetBreeds() }
            .onSuccess { return it.body() ?: BreedsResponse() }
            .onFailure { Log.e("ññ", "CatBreedsAPI ERROR MSJ: ${it.message}") }
        return null
    }

    suspend fun getCatCategoriesFromAPI(): CategoriesResponse? {
        runCatching { cat.getPetCategories() }
            .onSuccess { return it.body() ?: CategoriesResponse() }
            .onFailure { Log.e("ññ", "CatCategoriesAPI ERROR MSJ: ${it.message}") }
        return null
    }

    suspend fun getCatsByFilters(
        filterId: String,
        filterType: BreedOrCategoryFilter,
    ): List<SearchPetModel>? {

        runCatching {
            val breedId = if (filterType) filterId else ""
            val categoryId = if (!filterType) filterId else ""
            cat.getPetByFilter(SEARCH_PET_COUNT, breedId, categoryId)
        }
            .onSuccess { return it.body()?.toSearchModel(true) ?: emptyList() }
            .onFailure { Log.e("ññ", "CatByFilterAPI ERROR MSJ: ${it.message}") }
        return null
    }

}