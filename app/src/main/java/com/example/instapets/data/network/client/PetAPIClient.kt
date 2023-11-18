package com.example.instapets.data.network.client

import android.util.Log
import com.example.instapets.data.network.NetworkModule.CAT_SERVICE_NAME
import com.example.instapets.data.network.NetworkModule.DOG_SERVICE_NAME
import com.example.instapets.data.network.APIService
import com.example.instapets.data.network.response.BreedItem.BreedsResponse
import com.example.instapets.data.network.response.PetItem.PetResponse
import javax.inject.Inject
import javax.inject.Named

class PetAPIClient @Inject constructor(
    @Named(CAT_SERVICE_NAME) private val cat: APIService,
    @Named(DOG_SERVICE_NAME) private val dog: APIService
) {

    suspend fun getPetImagesFromAPI(): PetResponse? {
        runCatching {
            val catsCount = (1..PET_COUNT_DEFAULT_VALUE).random()
            val dogsCount = catsCount - PET_COUNT_DEFAULT_VALUE

            val catsResponse: PetResponse? = cat.getPetImages(catsCount).body()
            val dogsResponse: PetResponse? = dog.getPetImages(dogsCount).body()

            val combinationResponse = PetResponse()

            if (catsResponse != null && dogsResponse != null) {
                combinationResponse.addAll(catsResponse)
                combinationResponse.addAll(dogsResponse)
            }
            PetResponse(combinationResponse.shuffled())

        }
            .onSuccess { pets -> return pets }
            .onFailure { Log.e("ññ", "PetsImagesAPI ERROR MSJ: ${it.message}") }

        return null
    }

    suspend fun getPetBreedsFromAPI(): BreedsResponse? {
        runCatching {

            val catsResponse = cat.getPetBreeds().body()
            val dogsResponse = dog.getPetBreeds().body()

            val combinationResponse = BreedsResponse()

            if (catsResponse != null && dogsResponse != null) {
                combinationResponse.addAll(catsResponse)
                combinationResponse.addAll(dogsResponse)
            }

            combinationResponse

        }
            .onSuccess { breeds -> return breeds }
            .onFailure { Log.e("ññ", "PetsBreedsAPI ERROR MSJ: ${it.message}") }

        return null
    }

    companion object{
        const val PET_COUNT_DEFAULT_VALUE = 10
    }
}