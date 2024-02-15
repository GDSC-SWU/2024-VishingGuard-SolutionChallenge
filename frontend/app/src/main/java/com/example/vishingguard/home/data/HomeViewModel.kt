package com.example.vishingguard.home.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()

    // Home LiveData
    private val _postHome: MutableLiveData<HomeResponse> = MutableLiveData()  //read, write
    val postHome: LiveData<HomeResponse> = _postHome //read
    private val postHomeService = ServicePool.postHome

    // Server interaction
    fun postHome() {
        if (accessToken != null) {
            postHomeService.postHome(accessToken).enqueue(object : retrofit2.Callback<HomeResponse> {
                override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                    if (response.isSuccessful) {
                        _postHome.value = response.body()
                        Log.d("success postHome", _postHome.value.toString())
                    } else {
                        Log.d("error postHome", "Failed response")
                    }
                }

                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postHome", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}