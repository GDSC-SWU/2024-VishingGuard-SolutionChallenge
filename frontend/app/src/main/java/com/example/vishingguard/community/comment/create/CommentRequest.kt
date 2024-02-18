package com.example.vishingguard.community.comment.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    @SerialName("content")
    val content: String
)