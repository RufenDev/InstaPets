package com.example.instapets.domain.usecase

import com.example.instapets.core.PetTypes
import com.example.instapets.domain.Repository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPetImages @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(type: PetTypes) = withContext(IO) {
        repository.getPetImages(type)
    }
}