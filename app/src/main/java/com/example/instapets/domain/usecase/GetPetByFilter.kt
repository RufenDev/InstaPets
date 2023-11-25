package com.example.instapets.domain.usecase

import com.example.instapets.core.PetTypes
import com.example.instapets.domain.Repository
import com.example.instapets.domain.BreedOrCategoryFilter
import com.example.instapets.domain.model.filter.FilterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPetByFilter @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(
        filter: FilterModel,
        filterType: BreedOrCategoryFilter,
        petPreference: PetTypes
    ) = withContext(Dispatchers.IO) {
        repository.getPetsByFilter(filter, filterType, petPreference)
    }

}