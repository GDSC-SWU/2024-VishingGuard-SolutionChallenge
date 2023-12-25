package com.example.vishingguard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class SmishingResponse(
    @SerialName("smishing")
    val smishing: Boolean
)