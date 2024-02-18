package com.example.vishingguard

import com.example.vishingguard.community.CommunityApiService
import com.example.vishingguard.home.data.HomeApiService
import com.example.vishingguard.login.data.LoginApiService
import com.example.vishingguard.map.data.RouteApiService
import com.example.vishingguard.pishing.data.ReportApiService
import com.example.vishingguard.smishing.data.SmsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object ApiFactory {
    private const val BASE_URL =
        "http://35.216.77.51:8080"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}

object ServicePool {
    // Login
    val postSignUp = ApiFactory.retrofit.create(LoginApiService::class.java)
    val postLogin = ApiFactory.retrofit.create(LoginApiService::class.java)

    // Pishing
    val postSms = ApiFactory.retrofit.create(SmsApiService::class.java)
    val postVishing = ApiFactory.retrofit.create(ReportApiService::class.java)
    val postSmishing = ApiFactory.retrofit.create(ReportApiService::class.java)
    val getState = ApiFactory.retrofit.create(ReportApiService::class.java)

    // Home
    val postHome = ApiFactory.retrofit.create(HomeApiService::class.java)
    val getProcedure = ApiFactory.retrofit.create(HomeApiService::class.java)
    val getReport = ApiFactory.retrofit.create(HomeApiService::class.java)
    val getPrevention = ApiFactory.retrofit.create(HomeApiService::class.java)
    val postSpamCheck = ApiFactory.retrofit.create(HomeApiService::class.java)

    // Community
    val getPosts = ApiFactory.retrofit.create(CommunityApiService::class.java)
    val getRead = ApiFactory.retrofit.create(CommunityApiService::class.java)
    val postCreate = ApiFactory.retrofit.create(CommunityApiService::class.java)
    val deletePost = ApiFactory.retrofit.create(CommunityApiService::class.java)
    val updatePost = ApiFactory.retrofit.create(CommunityApiService::class.java)

    // Comment
    val getComment = ApiFactory.retrofit.create(CommunityApiService::class.java)
    val postComment = ApiFactory.retrofit.create(CommunityApiService::class.java)
    val patchComment = ApiFactory.retrofit.create(CommunityApiService::class.java)
    val deleteComment = ApiFactory.retrofit.create(CommunityApiService::class.java)

    // Map
    val postRoute = ApiFactory.retrofit.create(RouteApiService::class.java)
}