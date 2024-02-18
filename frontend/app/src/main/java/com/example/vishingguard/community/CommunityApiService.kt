package com.example.vishingguard.community

import com.example.vishingguard.community.comment.AllCommentResponse
import com.example.vishingguard.community.comment.create.CommentResponse
import com.example.vishingguard.community.comment.create.CommentRequest
import com.example.vishingguard.community.comment.create.DelCommentResponse
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
import retrofit2.http.PATCH
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

    @PATCH("/api/v1/posts/{userId}/{postId}/update")
    fun updatePost(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Path("postId") postId: String,
        @Body params: CreateRequest
    ): Call<CreateResponse>

    @GET("/api/v1/comments/{userId}/{postId}/read")
    fun getComment(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Path("postId") postId: String
    ): Call<AllCommentResponse>

    @POST("/api/v1/comments/{userId}/{postId}/create")
    fun postComment(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Path("postId") postId: String,
        @Body params: CommentRequest
    ): Call<CommentResponse>

    @PATCH("/api/v1/comments/{userId}/{postId}/{commentId}/update")
    fun patchComment(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Path("postId") postId: String,
        @Path("commentId") commentId: String,
        @Body params: CommentRequest
    ): Call<CommentResponse>

    @DELETE("/api/v1/comments/{userId}/{postId}/{commentId}/delete")
    fun deleteComment(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Int,
        @Path("postId") postId: String,
        @Path("commentId") commentId: String
    ): Call<DelCommentResponse>
}