package com.example.vishingguard.pishing.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VishingResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<VishingReport>
)

@Serializable
data class VishingReport(
    @SerialName("phone") val phone: String,
    @SerialName("date") val date: String,
    @SerialName("time") val time: String,
    @SerialName("keywordComment") val keywordComment: String
)