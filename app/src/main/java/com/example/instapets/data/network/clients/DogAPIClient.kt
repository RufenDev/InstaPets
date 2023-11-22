package com.example.instapets.data.network.clients

import android.util.Log
import com.example.instapets.data.network.NetworkModule.DOG_SERVICE_NAME
import com.example.instapets.data.network.APIService
import com.example.instapets.data.network.clients.PetAPIClient.Companion.PET_COUNT_DEFAULT_VALUE
import com.example.instapets.data.network.response.BreedItem
import com.example.instapets.data.network.response.PetItem
import com.example.instapets.data.network.response.PetItem.PetResponse
import javax.inject.Inject
import javax.inject.Named

class DogAPIClient @Inject constructor(
    @Named(DOG_SERVICE_NAME) private val dog: APIService
) {

    suspend fun getDogImagesFromAPI(): PetResponse? {
        runCatching { dog.getPetImages(PET_COUNT_DEFAULT_VALUE) }
            .onSuccess { return it.body() ?: PetResponse() }
            .onFailure { Log.e("ññ", "DogImagesAPI ERROR MSJ: ${it.message}") }
        return null
    }

    suspend fun getDogDescriptionFromAPI(id: String): PetItem? {
        runCatching { dog.getPetDescription(id) }
            .onSuccess { return it.body() ?: PetItem() }
            .onFailure { Log.e("ññ", "DogDescriptionAPI ERROR MSJ: ${it.message}") }
        return null
    }

    suspend fun getDogBreedsFromAPI(): BreedItem.BreedsResponse? {
        runCatching { dog.getPetBreeds() }
            .onSuccess { return it.body() ?: BreedItem.BreedsResponse() }
            .onFailure { Log.e("ññ", "DogBreedsAPI ERROR MSJ: ${it.message}") }
        return null
    }

    suspend fun getDogsByFilters(breedId: String, categoryId: String): PetResponse? {
        runCatching { dog.getPetByFilter(breedId, categoryId) }
            .onSuccess { return it.body() ?: PetResponse() }
            .onFailure { Log.e("ññ", "DogByFilterAPI ERROR MSJ: ${it.message}") }
        return null
    }

}