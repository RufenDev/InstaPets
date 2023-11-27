package com.example.instapets.domain.usecase

import com.example.instapets.domain.Repository
import com.example.instapets.domain.model.filter.IsACat
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPetDescription @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: String, type: IsACat) = withContext(IO){
        repository.getPetDescription(id, type)
    }

}