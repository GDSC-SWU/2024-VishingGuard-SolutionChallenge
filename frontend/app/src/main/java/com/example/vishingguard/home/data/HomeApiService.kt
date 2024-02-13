package com.example.vishingguard.home.data

import com.example.vishingguard.home.procedure.data.ProcedureResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApiService {

    @GET("/api/v1/info/report_procedure")
    fun getProcedure(
        @Header("Authorization") authorization: String
    ): Call<ProcedureResponse>
}