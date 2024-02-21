package com.example.vishingguard.pishing.vishing.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VishingRequest(
    @SerialName("vishingScript")
    val vishingScript: String,
    @SerialName("phone")
    val phone: String
)