package com.example.instapets.data.network.clients

import android.util.Log
import com.example.instapets.data.network.NetworkModule.CAT_SERVICE_NAME
import com.example.instapets.data.network.NetworkModule.DOG_SERVICE_NAME
import com.example.instapets.data.network.APIService
import com.example.instapets.data.network.APIService.Companion.DEFAULT_PET_COUNT
import com.example.instapets.data.network.APIService.Companion.SEARCH_PET_COUNT
import com.example.instapets.data.network.response.BreedItem.BreedsResponse
import com.example.instapets.data.network.response.PetItem.PetResponse
import com.example.instapets.domain.BreedOrCategoryFilter
import com.example.instapets.domain.model.filter.FilterModel
import javax.inject.Inject
import javax.inject.Named

class PetAPIClient @Inject constructor(
    @Named(CAT_SERVICE_NAME) private val cat: APIService,
    @Named(DOG_SERVICE_NAME) private val dog: APIService,
) {

    suspend fun getPetImagesFromAPI(count:Int = DEFAULT_PET_COUNT): PetResponse? {
        runCatching {
            val catsCount = (3..count).random()
            val dogsCount = count - catsCount

            val catsResponse: PetResponse? = cat.getPetImages(catsCount).body()
            val dogsResponse: PetResponse? = dog.getPetImages(dogsCount).body()

            val combinationResponse = PetResponse()

            if (catsResponse != null && dogsResponse != null) {
                combinationResponse.addAll(catsResponse)
                combinationResponse.addAll(dogsResponse)
            }

            combinationResponse

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

    suspend fun getPetsByFilters(
        filter: FilterModel,
        filterType: BreedOrCategoryFilter,
    ): PetResponse? {

        kotlin.runCatching {
            val breedId = if (filterType) filter.id else ""
            val categoryId = if (!filterType) filter.id else ""

            if(breedId.isBlank() && categoryId.isBlank()){
                getPetImagesFromAPI(SEARCH_PET_COUNT)

            } else if(filter.petType){
                cat.getPetByFilter(SEARCH_PET_COUNT, breedId, categoryId).body()

            } else {
                dog.getPetByFilter(SEARCH_PET_COUNT, breedId, categoryId).body()
            }
        }
            .onSuccess { pets -> return pets }
            .onFailure { Log.e("ññ", "PetsByFilterAPI ERROR MSJ: ${it.message}") }

        return null
    }
}