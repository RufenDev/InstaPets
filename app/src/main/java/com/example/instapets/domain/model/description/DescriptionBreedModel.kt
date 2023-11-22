package com.example.instapets.domain.model.description

import com.example.instapets.data.network.response.PetItem
import com.example.instapets.domain.model.MetricsModel

data class DescriptionBreedModel(
    //General properties
    val id: String,
    val name: String,
    val description: String,
    val weight: MetricsModel,
    val origin: String,
    val lifeSpan: String,
    val temperament: String,

    //Cat properties
    val altNames: String = "",
    val adaptability: Int = 0,
    val affectionLevel: Int = 0,
    val biddability: Int = 0,
    val catFriendly: Int = 0,
    val childFriendly: Int = 0,
    val dogFriendly: Int = 0,
    val energyLevel: Int = 0,
    val experimental: Int = 0,
    val grooming: Int = 0,
    val hairless: Int = 0,
    val healthIssues: Int = 0,
    val hypoallergenic: Int = 0,
    val indoor: Int = 0,
    val intelligence: Int = 0,
    val lap: Int = 0,
    val natural: Int = 0,
    val rare: Int = 0,
    val rex: Int = 0,
    val sheddingLevel: Int = 0,
    val shortLegs: Int = 0,
    val socialNeeds: Int = 0,
    val strangerFriendly: Int = 0,
    val suppressedTail: Int = 0,
    val vocalisation: Int = 0,

    //Dog properties
    val bredFor: String = "",
    val breedGroup: String = "",
    val height: MetricsModel? = null
) {
    companion object {
        fun PetItem.dogBreeds() = breeds.map { breed ->
            DescriptionBreedModel(
                id = breed.id,
                name = breed.name,
                description = breed.description,
                weight = MetricsModel(breed.weight.imperial, breed.weight.metric),
                origin = breed.origin,
                lifeSpan = breed.lifeSpan,
                temperament = breed.temperament,

                bredFor = breed.bredFor,
                breedGroup = breed.breedGroup,
                height = MetricsModel(breed.height!!.imperial, breed.height.imperial)
            )
        }

        fun PetItem.catBreeds() = breeds.map { breed ->
            DescriptionBreedModel(
                id = breed.id,
                name = breed.name,
                description = breed.description,
                weight = MetricsModel(breed.weight.imperial, breed.weight.metric),
                origin = breed.origin,
                lifeSpan = breed.lifeSpan,
                temperament = breed.temperament,

                altNames = breed.altNames,
                adaptability = breed.adaptability,
                affectionLevel = breed.affectionLevel,
                biddability = breed.biddability,
                catFriendly = breed.catFriendly,
                childFriendly = breed.childFriendly,
                dogFriendly = breed.dogFriendly,
                energyLevel = breed.energyLevel,
                experimental = breed.experimental,
                grooming = breed.grooming,
                hairless = breed.hairless,
                healthIssues = breed.healthIssues,
                hypoallergenic = breed.hypoallergenic,
                indoor = breed.indoor,
                intelligence = breed.intelligence,
                lap = breed.lap,
                natural = breed.natural,
                rare = breed.rare,
                rex = breed.rex,
                sheddingLevel = breed.sheddingLevel,
                shortLegs = breed.shortLegs,
                socialNeeds = breed.socialNeeds,
                strangerFriendly = breed.strangerFriendly,
                suppressedTail = breed.suppressedTail,
                vocalisation = breed.vocalisation
            )
        }
    }
}

