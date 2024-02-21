package com.example.vishingguard.stt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SttResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expire_at") val expireAt: Long
)