package com.example.vishingguard.login.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: LoginData
)

@Serializable
data class LoginData(
    @SerialName("jwtToken") val jwtToken: JwtToken,
    @SerialName("userId") val userId: Int
)

@Serializable
data class JwtToken(
    @SerialName("grantType") val grantType: String,
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String
)
