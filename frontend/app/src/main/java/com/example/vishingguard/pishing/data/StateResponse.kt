package com.example.vishingguard.pishing.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StateResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: String
)