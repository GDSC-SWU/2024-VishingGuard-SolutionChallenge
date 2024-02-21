package com.example.vishingguard.community.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: Create
)

@Serializable
data class Create(
    @SerialName("postId") val postId: String,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("username") val username: String,
    @SerialName("userId") val userId: Int,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("comment_count") val commentCount: Int,
    @SerialName("heart_count") val heartCount: Int,
    @SerialName("myPost") val myPost: Boolean
)