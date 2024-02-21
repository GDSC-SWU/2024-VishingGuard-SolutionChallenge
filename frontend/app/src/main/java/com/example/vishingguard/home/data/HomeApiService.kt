package com.example.vishingguard.home.data

import com.example.vishingguard.home.prevention.data.PreventionResponse
import com.example.vishingguard.home.procedure.data.ProcedureResponse
import com.example.vishingguard.home.report.data.ReportResponse
import com.example.vishingguard.home.spam.data.SpamCheckRequest
import com.example.vishingguard.home.spam.data.SpamCheckResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("/api/v1/home/")
    fun postHome(
        @Header("Authorization") accessToken: String,
    ): Call<HomeResponse>

    @GET("/api/v1/financial/download/{number}")
    fun getFss(
        @Header("Authorization") authorization: String,
        @Path("number") number: Int
    ): Call<ResponseBody>
}