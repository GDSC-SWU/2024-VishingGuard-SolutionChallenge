package com.example.vishingguard.stt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestConfig(
    @SerialName("use_diarization") val use_diarization: Boolean,
    @SerialName("diarization_spk_count") val diarization_spk_count: Int?,
    @SerialName("use_multi_channel") val use_multi_channel: Boolean,
    @SerialName("use_itn") val use_itn: Boolean,
    @SerialName("use_disfluency_filter") val use_disfluency_filter: Boolean,
    @SerialName("use_profanity_filter") val use_profanity_filter: Boolean,
    @SerialName("use_paragraph_splitter") val use_paragraph_splitter: Boolean,
    @SerialName("paragraph_splitter_max") val paragraph_splitter_max: Int,
    @SerialName("domain") val domain: String,
    @SerialName("use_wordtimestamp") val use_word_timestamp: Boolean,
    @SerialName("keywords") val keywords: List<String>
)