package com.example.instapets.data.network.response

import com.google.gson.annotations.SerializedName

data class MetricsItem(
    @SerializedName("imperial") val imperial: String = "",
    @SerializedName("metric") val metric: String = ""
)