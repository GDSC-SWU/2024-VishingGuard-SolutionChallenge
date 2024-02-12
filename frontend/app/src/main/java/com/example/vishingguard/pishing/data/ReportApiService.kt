package com.example.vishingguard.pishing.data

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportApiService {
    @POST("/api/v1/report/vishing/{userId}")
    fun postVishing(
        @Header("Authorization") accessToken: String,
        @Path("userId") userId: Int
    ): Call<VishingResponse>
}