package com.example.instapets.data.network

import com.example.instapets.data.network.client.CatAPIClient
import com.example.instapets.data.network.client.DogAPIClient
import com.example.instapets.data.network.client.PetAPIClient
import javax.inject.Inject

data class APIClient @Inject constructor(
    val cat: CatAPIClient,
    val dog: DogAPIClient,
    val pets: PetAPIClient
)