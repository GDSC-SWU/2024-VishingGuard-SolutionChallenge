package com.example.vishingguard.home.prevention.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreventionResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<Prevention>
)

@Serializable
data class Prevention(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("order") val order: Int
)