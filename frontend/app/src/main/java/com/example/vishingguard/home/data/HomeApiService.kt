package com.example.vishingguard.home.data

import com.example.vishingguard.home.procedure.data.ProcedureResponse
import com.example.vishingguard.home.report.data.ReportResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApiService {

    @GET("/api/v1/info/reportProcedure")
    fun getProcedure(
        @Header("Authorization") authorization: String
    ): Call<ProcedureResponse>

    @GET("/api/v1/info/reportPlace")
    fun getReport(
        @Header("Authorization") authorization: String
    ): Call<ReportResponse>
}