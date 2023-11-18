package com.example.instapets.data.network.response

import com.example.instapets.data.network.response.BreedItem.BreedsResponse
import com.example.instapets.data.network.response.CategoryItem.CategoriesResponse
import com.google.gson.annotations.SerializedName

data class PetItem(
    @SerializedName("id") val id: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("breeds") val breeds: BreedsResponse = BreedsResponse(),
    @SerializedName("categories") val categories: CategoriesResponse = CategoriesResponse(),
    @SerializedName("height") val height: Int = 0,
    @SerializedName("width") val width: Int = 0,
) {
    data class PetResponse(val list : List<PetItem> = emptyList()) : ArrayList<PetItem>(list)
}
