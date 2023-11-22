package com.example.instapets.domain.usecase

import com.example.instapets.core.PetTypes
import com.example.instapets.core.PetTypes.CAT
import com.example.instapets.core.PetTypes.PETS
import com.example.instapets.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPetCategories @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(type : PetTypes) =
        withContext(Dispatchers.IO){
            if(type == CAT || type == PETS){
                repository.getCategoriesList()
            } else {
                emptyList()
            }
        }

}