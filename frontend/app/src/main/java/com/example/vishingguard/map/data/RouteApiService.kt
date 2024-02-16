package com.example.vishingguard.map.data

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface RouteApiService {
    @POST("/api/v1/route/")
    fun postRoute(
        @Header("Authorization") accessToken: String,
    ): Call<RouteResponse>
}