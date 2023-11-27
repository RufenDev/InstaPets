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
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.domain.model.home.HomePetModel.Companion.toHomeModel
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.domain.model.search.SearchPetModel.Companion.toSearchModel
import javax.inject.Inject
import javax.inject.Named

class PetAPIClient @Inject constructor(
    @Named(CAT_SERVICE_NAME) private val cat: APIService,
    @Named(DOG_SERVICE_NAME) private val dog: APIService,
) {

    suspend fun getPetImagesFromAPI(count:Int = DEFAULT_PET_COUNT): List<HomePetModel>? {
        runCatching {
            val catsCount = (3..count).random()
            val dogsCount = count - catsCount

            val catsResponse: PetResponse? = cat.getPetImages(catsCount).body()
            val dogsResponse: PetResponse? = dog.getPetImages(dogsCount).body()

            val combinationResponse = mutableListOf<HomePetModel>()

            if (catsResponse != null && dogsResponse != null) {
                combinationResponse.addAll(catsResponse.toHomeModel(true))
                combinationResponse.addAll(dogsResponse.toHomeModel(false))
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
    ): List<SearchPetModel>? {

        kotlin.runCatching {
            val breedId = if (filterType) filter.id else ""
            val categoryId = if (!filterType) filter.id else ""

            if(breedId.isBlank() && categoryId.isBlank()){
                getPetImagesFromAPI(SEARCH_PET_COUNT)?.toSearchModel() ?: emptyList()

            } else if(filter.petType){
                cat.getPetByFilter(SEARCH_PET_COUNT, breedId, categoryId)
                    .body()?.toSearchModel(true) ?: emptyList()

            } else {
                dog.getPetByFilter(SEARCH_PET_COUNT, breedId, categoryId)
                    .body()?.toSearchModel(false) ?: emptyList()
            }
        }
            .onFailure { Log.e("ññ", "PetsByFilterAPI ERROR MSJ: ${it.message}") }
            .onSuccess { pets -> return pets }

        return null
    }
}