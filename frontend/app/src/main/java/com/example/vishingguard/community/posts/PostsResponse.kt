package com.example.vishingguard.community.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<Post>
)

@Serializable
data class Post(
    @SerialName("postId") val postId: String,
    @SerialName("title") val title: String,
    @SerialName("username") val username: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("content") val content: String,
    @SerialName("comment_count") val commentCount: Int,
    @SerialName("heart_count") val heartCount: Int
)