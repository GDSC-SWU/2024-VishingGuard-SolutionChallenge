package com.example.vishingguard.stt

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object SttApiFactory {
    private const val BASE_URL =
        "https://openapi.vito.ai/v1/"

    val json = Json { ignoreUnknownKeys = true }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}

object SttServicePool {
    val postStt = SttApiFactory.retrofit.create(SttApiService::class.java)
    val postTranscribe = SttApiFactory.retrofit.create(SttApiService::class.java)
    val getTranscribe = SttApiFactory.retrofit.create(SttApiService::class.java)
}