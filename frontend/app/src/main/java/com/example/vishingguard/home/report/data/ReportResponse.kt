package com.example.vishingguard.home.report.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<Report>
)

@Serializable
data class Report(
    @SerialName("id") val id: String?,
    @SerialName("number") val number: String?,
    @SerialName("place") val place: String?,
    @SerialName("order") val order: Int?,
    @SerialName("tag") val tag: String?
)