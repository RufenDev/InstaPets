package com.example.instapets.data.network.response

import com.google.gson.annotations.SerializedName

data class BreedItem(

    //General properties
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("weight") val weight: MetricsItem,
    @SerializedName("origin") val origin: String,
    @SerializedName("life_span") val lifeSpan: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("reference_image_id") val referenceImageId: String,

    //Cat properties
    @SerializedName("adaptability") val adaptability: Int = 0,
    @SerializedName("affection_level") val affectionLevel: Int = 0,
    @SerializedName("alt_names") val altNames: String = "",
    @SerializedName("bidability") val biddability: Int = 0,
    @SerializedName("cat_friendly") val catFriendly: Int = 0,
    @SerializedName("cfa_url") val cfaURL: String = "",
    @SerializedName("child_friendly") val childFriendly: Int = 0,
    @SerializedName("country_codes") val countryCodes: String = "",
    @SerializedName("dog_friendly") val dogFriendly: Int = 0,
    @SerializedName("energy_level") val energyLevel: Int = 0,
    @SerializedName("experimental") val experimental: Int = 0,
    @SerializedName("grooming") val grooming: Int = 0,
    @SerializedName("hairless") val hairless: Int = 0,
    @SerializedName("health_issues") val healthIssues: Int = 0,
    @SerializedName("hypoallergenic") val hypoallergenic: Int = 0,
    @SerializedName("indoor") val indoor: Int = 0,
    @SerializedName("intelligence") val intelligence: Int = 0,
    @SerializedName("lap") val lap: Int = 0,
    @SerializedName("natural") val natural: Int = 0,
    @SerializedName("rare") val rare: Int = 0,
    @SerializedName("rex") val rex: Int = 0,
    @SerializedName("shedding_level") val sheddingLevel: Int = 0,
    @SerializedName("short_legs") val shortLegs: Int = 0,
    @SerializedName("social_needs") val socialNeeds: Int = 0,
    @SerializedName("stranger_friendly") val strangerFriendly: Int = 0,
    @SerializedName("suppressed_tail") val suppressedTail: Int = 0,
    @SerializedName("vcahospitals_url") val vcaHospitalsURL: String = "",
    @SerializedName("vetstreet_url") val vetStreetURL: String = "",
    @SerializedName("vocalisation") val vocalisation: Int = 0,
    @SerializedName("wikipedia_url") val wikipediaURL: String = "",

    //Dog properties
    @SerializedName("bredFor") val bredFor: String = "",
    @SerializedName("breedGroup") val breedGroup: String = "",
    @SerializedName("height") val height: MetricsItem? = null

) {
    class BreedsResponse : ArrayList<BreedItem>()
}