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

class UpdateCommentViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()
    val userId = UserData.getUserId()
    val postId = PostData.getPostId()
    val commentId = PostData.getCommentId()

    // UpdateComment LiveData
    private val _patchComment: MutableLiveData<CommentResponse> = MutableLiveData()  //read, write
    val patchComment: LiveData<CommentResponse> = _patchComment //read
    private val patchCommentService = ServicePool.patchComment

    // Server interaction
    fun patchComment(postComment: CommentRequest) {
        if (accessToken != null && userId != null && postId != null && commentId != null) {
            patchCommentService.patchComment(accessToken, userId, postId, commentId, postComment).enqueue(object : retrofit2.Callback<CommentResponse> {
                override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                    if (response.isSuccessful) {
                        _patchComment.value = response?.body()
                        Log.d("success patchComment", _patchComment.value.toString())
                    } else {
                        Log.d("error patchComment", "Failed response")
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    t.message?.let { Log.d("error patchComment", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}