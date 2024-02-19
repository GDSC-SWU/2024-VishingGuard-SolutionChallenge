package com.example.vishingguard.mypage

import com.example.vishingguard.mypage.data.WithdrawUserRequest
import com.example.vishingguard.mypage.data.UpdateUserRequest
import com.example.vishingguard.mypage.data.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MyPageApiService {
    @POST("/api/v1/myPage/{userId}/modify")
    fun updateUser(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Body params: UpdateUserRequest
    ): Call<UserResponse>

    @POST("/api/v1/myPage/{userId}/withdraw")
    fun withdrawUser(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Body params: WithdrawUserRequest
    ): Call<UserResponse>
}