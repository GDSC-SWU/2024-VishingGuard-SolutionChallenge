package com.example.vishingguard.vishing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class VishingResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Boolean
)