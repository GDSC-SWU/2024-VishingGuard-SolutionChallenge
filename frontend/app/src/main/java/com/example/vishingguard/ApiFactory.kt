package com.example.vishingguard

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object ApiFactory {
    private const val BASE_URL =
        "https://2b43a12b-51c2-41ce-88ca-35dc29deefa4.mock.pstmn.io/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}

object ServicePool {
    val postSmishing = ApiFactory.retrofit.create(SmishingApiService::class.java)
}