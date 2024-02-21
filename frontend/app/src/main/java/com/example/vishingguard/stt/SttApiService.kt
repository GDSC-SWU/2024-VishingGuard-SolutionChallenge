package com.example.vishingguard.stt

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SttApiService {

    @POST("authenticate")
    @FormUrlEncoded
    fun authenticate(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<SttResponse>

    @Multipart
    @POST("transcribe")
    fun postTranscribe(
        @Header("Authorization") accessToken: String,
        @Part("config") config: RequestConfig,
        @Part file: MultipartBody.Part
    ): Call<TranscribeResponse>

    @GET("transcribe/{transcribeId}")
    fun getTranscribe(
        @Header("Authorization") accessToken: String,
        @Path("transcribeId") transcribeId: String
    ): Call<GetTranscribeResponse>
}