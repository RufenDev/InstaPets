package com.example.instapets.domain.usecase

import com.example.instapets.core.PetTypes
import com.example.instapets.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPetByFilter @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(breedId: String, categoryId: String, type: PetTypes) =
        withContext(Dispatchers.IO) {
            repository.getPetsByFilter(breedId, categoryId, type)
        }

}