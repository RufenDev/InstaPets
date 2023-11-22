package com.example.instapets.domain.model.search

import com.example.instapets.data.network.response.PetItem

data class SearchPetModel(val id:String, val image:String) {

    companion object{
        fun PetItem.PetResponse.toSearchModel() = map { pet ->
            SearchPetModel(pet.id, pet.url)
        }.shuffled()
    }

}