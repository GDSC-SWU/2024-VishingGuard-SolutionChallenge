package com.example.vishingguard.community.read

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.community.PostData
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class ReadViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()
    val userId = UserData.getUserId()
    val postId = PostData.getPostId()

    // Read LiveData
    private val _getRead: MutableLiveData<ReadResponse> = MutableLiveData()  //read, write
    val getRead: LiveData<ReadResponse> = _getRead //read
    private val getReadService = ServicePool.getRead

    // Server interaction
    fun getRead() {
        if (accessToken != null && userId != null && postId != null) {
            getReadService.getRead(accessToken, userId, postId).enqueue(object : retrofit2.Callback<ReadResponse> {
                override fun onResponse(call: Call<ReadResponse>, response: Response<ReadResponse>) {
                    if (response.isSuccessful) {
                        _getRead.value = response?.body()
                        Log.d("success getRead", _getRead.value.toString())
                    } else {
                        Log.d("error getRead", "Failed response")
                    }
                }

                override fun onFailure(call: Call<ReadResponse>, t: Throwable) {
                    t.message?.let { Log.d("error getRead", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}