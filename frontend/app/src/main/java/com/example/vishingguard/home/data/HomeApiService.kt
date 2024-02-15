package com.example.vishingguard.home.data

import com.example.vishingguard.home.prevention.data.PreventionResponse
import com.example.vishingguard.home.procedure.data.ProcedureResponse
import com.example.vishingguard.home.report.data.ReportResponse
import com.example.vishingguard.home.spam.data.SpamCheckRequest
import com.example.vishingguard.home.spam.data.SpamCheckResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HomeApiService {

    @GET("/api/v1/info/reportProcedure")
    fun getProcedure(
        @Header("Authorization") authorization: String
    ): Call<ProcedureResponse>

    @GET("/api/v1/info/reportPlace")
    fun getReport(
        @Header("Authorization") authorization: String
    ): Call<ReportResponse>

    @GET("/api/v1/info/prevention")
    fun getPrevention(
        @Header("Authorization") authorization: String
    ): Call<PreventionResponse>

    @POST("/api/v1/result/spamNumber")
    fun postSpamCheck(
        @Header("Authorization") accessToken: String,
        @Body spamCheckRequest: SpamCheckRequest
    ): Call<SpamCheckResponse>
}