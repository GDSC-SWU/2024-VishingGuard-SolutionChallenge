package com.example.vishingguard.pishing.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportApiService {
    @POST("/api/v1/report/vishing/{userId}")
    fun postVishing(
        @Header("Authorization") accessToken: String,
        @Path("userId") userId: Int
    ): Call<VishingResponse>

    @POST("/api/v1/report/smishing/{userId}")
    fun postSmishing(
        @Header("Authorization") accessToken: String,
        @Path("userId") userId: Int
    ): Call<SmishingResponse>

    @GET("/api/v1/report/state/{userId}")
    fun getState(
        @Header("Authorization") accessToken: String,
        @Path("userId") userId: Int
    ): Call<StateResponse>
}