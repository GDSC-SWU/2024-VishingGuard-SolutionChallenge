package com.example.vishingguard.mypage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawUserRequest(
    @SerialName("password")
    val password: String
)