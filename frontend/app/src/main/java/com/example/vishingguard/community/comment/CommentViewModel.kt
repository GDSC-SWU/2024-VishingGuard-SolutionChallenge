package com.example.vishingguard.community.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.community.PostData
import com.example.vishingguard.community.comment.create.CommentRequest
import com.example.vishingguard.community.comment.create.CommentResponse
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class CommentViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()
    val userId = UserData.getUserId()
    val postId = PostData.getPostId()

    // GetComment LiveData
    private val _getComment: MutableLiveData<AllCommentResponse> = MutableLiveData()  //read, write
    val getComment: LiveData<AllCommentResponse> = _getComment //read
    private val getCommentService = ServicePool.getComment

    private val _postComment: MutableLiveData<CommentResponse> = MutableLiveData()  //read, write
    val postComment: LiveData<CommentResponse> = _postComment //read
    private val postCommentService = ServicePool.postComment

    // Server interaction
    fun getComment() {
        if (accessToken != null && userId != null && postId != null) {
            getCommentService.getComment(accessToken, userId, postId).enqueue(object : retrofit2.Callback<AllCommentResponse> {
                override fun onResponse(call: Call<AllCommentResponse>, response: Response<AllCommentResponse>) {
                    if (response.isSuccessful) {
                        _getComment.value = response?.body()
                        Log.d("getComment","userId :${userId}")
                        Log.d("success getComment", _getComment.value.toString())
                    } else {
                        Log.d("error getComment", "Failed response")
                    }
                }

                override fun onFailure(call: Call<AllCommentResponse>, t: Throwable) {
                    t.message?.let { Log.d("error getComment", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }

    fun postComment(postComment: CommentRequest) {
        if (accessToken != null && userId != null && postId != null) {
            postCommentService.postComment(accessToken, userId, postId, postComment).enqueue(object : retrofit2.Callback<CommentResponse> {
                override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                    if (response.isSuccessful) {
                        _postComment.value = response?.body()
                        Log.d("success postComment", _postComment.value.toString())
                    } else {
                        Log.d("error postComment", "Failed response")
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postComment", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}