package com.example.instapets.data.network.clients

import android.util.Log
import com.example.instapets.data.network.NetworkModule.CAT_SERVICE_NAME
import com.example.instapets.data.network.APIService
import com.example.instapets.data.network.clients.PetAPIClient.Companion.PET_COUNT_DEFAULT_VALUE
import com.example.instapets.data.network.response.BreedItem.BreedsResponse
import com.example.instapets.data.network.response.CategoryItem.CategoriesResponse
import com.example.instapets.data.network.response.PetItem
import com.example.instapets.data.network.response.PetItem.PetResponse
import javax.inject.Inject
import javax.inject.Named

class CatAPIClient @Inject constructor(
    @Named(CAT_SERVICE_NAME) private val cat: APIService
) {

    suspend fun getCatImagesFromAPI(): PetResponse? {
        runCatching { cat.getPetImages(PET_COUNT_DEFAULT_VALUE) }
            .onSuccess { return it.body() ?: PetResponse() }
            .onFailure { Log.e("ññ", "CatImagesAPI ERROR MSJ: ${it.message}") }
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

    suspend fun getCatsByFilters(breedId: String, categoryId: String): PetResponse? {
        runCatching { cat.getPetByFilter(breedId, categoryId) }
            .onSuccess { return it.body() ?: PetResponse() }
            .onFailure { Log.e("ññ", "CatByFilterAPI ERROR MSJ: ${it.message}") }
        return null
    }

}