package com.example.vishingguard.login.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/members/sign-up")
    fun postSignUp(
        @Body params: SignUpRequest
    ): Call<SignUpResponse>
}