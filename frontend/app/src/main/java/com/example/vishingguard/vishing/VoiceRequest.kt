package com.example.vishingguard.vishing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoiceRequest(
    @SerialName("text") val text: String,
)