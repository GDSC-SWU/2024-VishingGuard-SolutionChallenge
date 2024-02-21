package com.example.vishingguard.stt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTranscribeResponse(
    @SerialName("id") val id: String,
    @SerialName("status") val status: String,
    @SerialName("results") val results: TranscribeResults? = null
)

@Serializable
data class TranscribeResults(
    @SerialName("utterances") val utterances: List<TranscribeUtterance>
)
@Serializable
data class TranscribeUtterance(
    @SerialName("start_at") val start_at: Int,
    @SerialName("duration") val duration: Int,
    @SerialName("msg") val msg: String,
    @SerialName("spk") val spk: Int
)