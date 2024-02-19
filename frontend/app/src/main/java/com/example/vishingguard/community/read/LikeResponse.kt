package com.example.vishingguard.community.read

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: LikeData
)

@Serializable
data class LikeData(
    @SerialName("userId") val userId: Int,
    @SerialName("postId") val postId: String,
    @SerialName("heart") val heart: Boolean
)