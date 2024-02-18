package com.example.vishingguard.community.comment.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DelCommentResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: String
)