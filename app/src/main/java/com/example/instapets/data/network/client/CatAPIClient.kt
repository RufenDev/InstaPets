package com.example.instapets.data.network.client

import android.util.Log
import com.example.instapets.data.network.NetworkModule.CAT_SERVICE_NAME
import com.example.instapets.data.network.APIService
import com.example.instapets.data.network.client.PetAPIClient.Companion.PET_COUNT_DEFAULT_VALUE
import com.example.instapets.data.network.response.BreedItem
import com.example.instapets.data.network.response.CategoryItem
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

    suspend fun getCatBreedsFromAPI(): BreedItem.BreedsResponse? {
        runCatching { cat.getPetBreeds() }
            .onSuccess { return it.body() ?: BreedItem.BreedsResponse() }
            .onFailure { Log.e("ññ", "CatBreedsAPI ERROR MSJ: ${it.message}") }
        return null
    }

    suspend fun getCatCategoriesFromAPI(): CategoryItem.CategoriesResponse? {
        runCatching { cat.getPetCategories() }
            .onSuccess { return it.body() ?: CategoryItem.CategoriesResponse() }
            .onFailure { Log.e("ññ", "CatCategoriesAPI ERROR MSJ: ${it.message}") }
        return null
    }


}