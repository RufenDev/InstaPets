package com.example.instapets.data.network

import com.example.instapets.data.network.clients.CatAPIClient
import com.example.instapets.data.network.clients.DogAPIClient
import com.example.instapets.data.network.clients.PetAPIClient
import javax.inject.Inject

data class APIClient @Inject constructor(
    val cat: CatAPIClient,
    val dog: DogAPIClient,
    val pets: PetAPIClient
)