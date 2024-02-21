package com.example.vishingguard.vishing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoiceResponse(
    @SerialName("is_phishing") val is_phishing: Boolean
)