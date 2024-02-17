package com.example.vishingguard.community.delete

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.community.PostData
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class DeleteViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()
    val userId = UserData.getUserId()
    val postId = PostData.getPostId()

    // Delete LiveData
    private val _deletePost: MutableLiveData<DeleteResponse> = MutableLiveData()  //read, write
    val deletePost: LiveData<DeleteResponse> = _deletePost //read
    private val deletePostService = ServicePool.deletePost

    // Server interaction
    fun deletePost() {
        if (accessToken != null && userId != null && postId != null) {
            deletePostService.deletePost(accessToken, userId, postId).enqueue(object : retrofit2.Callback<DeleteResponse> {
                override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                    if (response.isSuccessful) {
                        _deletePost.value = response?.body()
                        Log.d("success deletePost", _deletePost.value.toString())
                    } else {
                        Log.d("error deletePost", "Failed response")
                    }
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                    t.message?.let { Log.d("error deletePost", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}