package com.example.vishingguard.smishing

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface SmishingApiService {
    @POST("api/v1/smishing")
    fun postSmishing(
        @Query("message") message: String
    ): Call<SmishingResponse>
}