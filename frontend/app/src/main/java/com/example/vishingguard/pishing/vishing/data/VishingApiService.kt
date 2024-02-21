package com.example.vishingguard.pishing.vishing.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface VishingApiService {
    @POST("/api/v1/result/vishing/{userId}")
    fun postCall(
        @Header("Authorization") accessToken: String,
        @Path("userId") userId: Int,
        @Body vishingRequest: VishingRequest
    ): Call<VishingResponse>
}