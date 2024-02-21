package com.example.vishingguard.mypage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)