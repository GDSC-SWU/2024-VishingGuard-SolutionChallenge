package com.example.vishingguard.home.spam.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpamCheckResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: SpamData
)

@Serializable
data class SpamData(
    @SerialName("result") val result: Boolean,
    @SerialName("name") val name: String?,
    @SerialName("count") val count: Int
)