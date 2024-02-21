package com.example.vishingguard.vishing

import com.example.vishingguard.ApiFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object VoiceApiFactory {
    private const val BASE_URL =
        "http://34.22.94.40:8081/"

    val json = Json { ignoreUnknownKeys = true }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}

object ServicePool {
    // Pishing Check
    val postVoice = VoiceApiFactory.retrofit.create(VoiceApiService::class.java)
}