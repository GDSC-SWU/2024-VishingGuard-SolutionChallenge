package com.example.vishingguard.smishing.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsRequest(
    @SerialName("smishingScript")
    val smishingScript: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String
)