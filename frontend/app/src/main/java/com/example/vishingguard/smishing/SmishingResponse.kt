package com.example.vishingguard.smishing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmishingResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Boolean
)