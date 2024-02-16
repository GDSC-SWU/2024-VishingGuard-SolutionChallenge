package com.example.vishingguard.map.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<Route>
)

@Serializable
data class Route(
    @SerialName("id") val id: String,
    @SerialName("institution") val institution: String,
    @SerialName("x") val x: Double,
    @SerialName("y") val y: Double
)