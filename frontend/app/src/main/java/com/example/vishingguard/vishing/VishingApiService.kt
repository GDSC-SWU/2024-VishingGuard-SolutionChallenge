package com.example.vishingguard.vishing

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface VishingApiService {
    @POST("api/v1/vishing")
    fun postVishing(
        @Query("message") message: String
    ): Call<VishingResponse>
}