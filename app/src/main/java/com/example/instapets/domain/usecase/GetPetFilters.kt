package com.example.instapets.domain.usecase

import com.example.instapets.core.PetTypes
import com.example.instapets.domain.Repository
import com.example.instapets.domain.BreedOrCategoryFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPetFilters @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(filter: BreedOrCategoryFilter, type: PetTypes) =
        withContext(Dispatchers.IO) {
            repository.getPetFilters(filter, type)
        }
}