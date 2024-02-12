package com.example.vishingguard.pishing.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class ReportViewModel : ViewModel() {
    // userData
    val userId = UserData.getUserId()
    val accessToken = UserData.getToken()

    // Vishing LiveData
    private val _postVishing: MutableLiveData<VishingResponse> = MutableLiveData()  //read, write
    val postVishing: LiveData<VishingResponse> = _postVishing //read
    private val postVishingService = ServicePool.postVishing

    // Server interaction
    fun postVishing() {
        if (accessToken != null && userId != null) {
            postVishingService.postVishing(accessToken, userId).enqueue(object : retrofit2.Callback<VishingResponse> {
                override fun onResponse(call: Call<VishingResponse>, response: Response<VishingResponse>) {
                    if (response.isSuccessful) {
                        _postVishing.value = response?.body()
                        Log.d("success postVishing", _postVishing.value.toString())
                    } else {
                        Log.d("error postVishing", "Failed response")
                    }
                }

                override fun onFailure(call: Call<VishingResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postVishing", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}