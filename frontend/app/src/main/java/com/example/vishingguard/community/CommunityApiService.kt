package com.example.vishingguard.community

import com.example.vishingguard.community.posts.PostsResponse
import com.example.vishingguard.community.read.ReadResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CommunityApiService {
    @GET("/api/v1/posts/read")
    fun getPosts(
        @Header("Authorization") authorization: String
    ): Call<PostsResponse>

    @GET("/api/v1/posts/{userId}/{postId}/read")
    fun getRead(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Path("postId") postId: String
    ): Call<ReadResponse>
}