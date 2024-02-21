package com.example.vishingguard.stt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranscribeResponse(
    @SerialName("id") val id: String
)