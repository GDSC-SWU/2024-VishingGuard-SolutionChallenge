package com.example.vishingguard.community

import com.example.vishingguard.community.create.CreateRequest
import com.example.vishingguard.community.create.CreateResponse
import com.example.vishingguard.community.delete.DeleteResponse
import com.example.vishingguard.community.posts.PostsResponse
import com.example.vishingguard.community.read.ReadResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

    @POST("/api/v1/posts/{userId}/create")
    fun postCreate(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Body params: CreateRequest
    ): Call<CreateResponse>

    @DELETE("/api/v1/posts/{userId}/{postId}/delete")
    fun deletePost(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Path("postId") postId: String
    ): Call<DeleteResponse>
}