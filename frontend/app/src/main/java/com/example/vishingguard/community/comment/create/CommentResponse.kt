package com.example.vishingguard.community.comment.create

import com.example.vishingguard.community.comment.Comment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: Comment
)