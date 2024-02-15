package com.example.vishingguard.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<Home>
)

@Serializable
data class Home(
    @SerialName("title") val title: String
)