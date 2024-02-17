package com.example.vishingguard.community.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String
)