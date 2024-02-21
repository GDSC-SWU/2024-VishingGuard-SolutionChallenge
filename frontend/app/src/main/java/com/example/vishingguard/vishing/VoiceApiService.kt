package com.example.vishingguard.vishing

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface VoiceApiService {
    @POST("predict")
    fun postVoice(
        @Body requestBody: VoiceRequest
    ): Call<VoiceResponse>
}