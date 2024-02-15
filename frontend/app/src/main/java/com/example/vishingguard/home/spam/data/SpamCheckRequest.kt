package com.example.vishingguard.home.spam.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpamCheckRequest(
    @SerialName("spamNumber") val spamNumber: String
)