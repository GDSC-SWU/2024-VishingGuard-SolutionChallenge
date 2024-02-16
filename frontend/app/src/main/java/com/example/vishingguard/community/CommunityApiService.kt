package com.example.vishingguard.community

import com.example.vishingguard.community.posts.PostsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CommunityApiService {
    @GET("/api/v1/posts/read")
    fun getPosts(
        @Header("Authorization") authorization: String
    ): Call<PostsResponse>
}