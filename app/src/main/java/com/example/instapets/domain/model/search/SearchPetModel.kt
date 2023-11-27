package com.example.instapets.domain.model.search

import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.filter.IsACat
import com.example.instapets.domain.model.home.HomePetModel

data class SearchPetModel(val id:String, val image:String, val type:IsACat) {

    companion object{
        fun PetItem.PetResponse.toSearchModel(type: IsACat) = map {
            SearchPetModel(it.id, it.url, type)
        }.shuffled()

        fun List<HomePetModel>.toSearchModel() = map{
            SearchPetModel(it.id, it.image, it.type)
        }

        //fun List<SearchPetModel>.log() = joinToString (" | ") { "id=${it.id}, type=${it.type}" }
    }

}