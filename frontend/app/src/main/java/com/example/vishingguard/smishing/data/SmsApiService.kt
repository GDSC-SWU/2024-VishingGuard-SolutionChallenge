package com.example.vishingguard.smishing.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SmsApiService {
    @POST("/api/v1/result/smishing/{userId}")
    fun postSms(
        @Header("Authorization") accessToken: String,
        @Path("userId") userId: Int,
        @Body smsRequest: SmsRequest
    ): Call<SmsResponse>
}