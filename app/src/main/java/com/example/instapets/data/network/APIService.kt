package com.example.instapets.data.network

import com.example.instapets.data.network.response.BreedItem.BreedsResponse
import com.example.instapets.data.network.response.CategoryItem.CategoriesResponse
import com.example.instapets.data.network.response.PetItem
import com.example.instapets.data.network.response.PetItem.PetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("images/search")
    suspend fun getPetImages(@Query("limit") petCount: Int): Response<PetResponse>

    @GET("images/{pet_id}")
    suspend fun getPetDescription(@Path("pet_id") id: String): Response<PetItem>

    @GET("breeds")
    suspend fun getPetBreeds(): Response<BreedsResponse>

    @GET("categories")
    suspend fun getPetCategories(): Response<CategoriesResponse>

    @GET("images/search")
    suspend fun getPetByFilter(
        @Query("breed_ids") breedId: String,
        @Query("category_ids") categoryId: String
    ) : Response<PetResponse>
}