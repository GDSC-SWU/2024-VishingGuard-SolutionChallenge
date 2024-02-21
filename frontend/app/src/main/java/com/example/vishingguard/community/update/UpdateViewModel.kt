package com.example.vishingguard.community.update

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.community.PostData
import com.example.vishingguard.community.create.CreateRequest
import com.example.vishingguard.community.create.CreateResponse
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class UpdateViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()
    val userId = UserData.getUserId()
    val postId = PostData.getPostId()

    // Update LiveData
    private val _updatePost: MutableLiveData<CreateResponse> = MutableLiveData()  //read, write
    val updatePost: LiveData<CreateResponse> = _updatePost //read
    private val updatePosteService = ServicePool.updatePost

    // Server interaction
    fun updatePost(createRequest: CreateRequest) {
        if (accessToken != null && userId != null && postId != null) {
            updatePosteService.updatePost(accessToken, userId, postId, createRequest).enqueue(object : retrofit2.Callback<CreateResponse> {
                override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                    if (response.isSuccessful) {
                        _updatePost.value = response?.body()
                        Log.d("success updatePost", _updatePost.value.toString())
                    } else {
                        Log.d("error updatePost", "Failed response")
                    }
                }

                override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                    t.message?.let { Log.d("error updatePost", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}