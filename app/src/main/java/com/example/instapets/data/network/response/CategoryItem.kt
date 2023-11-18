package com.example.instapets.data.network.response

import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
) {
    class CategoriesResponse : ArrayList<CategoryItem>()
}
