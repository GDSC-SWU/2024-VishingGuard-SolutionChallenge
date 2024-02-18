package com.example.vishingguard.community.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllCommentResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<Comment>
)

@Serializable
data class Comment(
    @SerialName("commentId") val commentId: String,
    @SerialName("postId") val postId: String,
    @SerialName("userId") val userId: Int,
    @SerialName("username") val username: String,
    @SerialName("content") val content: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("profile_image") val profileImage: String?,
    @SerialName("authorComment") val authorComment: Boolean
)