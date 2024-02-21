package com.example.vishingguard.community.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class CreateViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()
    val userId = UserData.getUserId()

    // Create LiveData
    private val _postCreate: MutableLiveData<CreateResponse> = MutableLiveData()  //read, write
    val postCreate: LiveData<CreateResponse> = _postCreate //read
    private val postCreateService = ServicePool.postCreate

    // Server interaction
    fun postCreate(createRequest: CreateRequest) {
        if (accessToken != null && userId != null) {
            postCreateService.postCreate(accessToken, userId, createRequest).enqueue(object : retrofit2.Callback<CreateResponse> {
                override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                    if (response.isSuccessful) {
                        _postCreate.value = response?.body()
                        Log.d("success postCreate", _postCreate.value.toString())
                    } else {
                        Log.d("error postCreate", "Failed response")
                    }
                }

                override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postCreate", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}