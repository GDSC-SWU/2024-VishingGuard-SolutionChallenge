package com.example.vishingguard.home.prevention.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class PreventionViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()

    // Prevention LiveData
    private val _getPrevention: MutableLiveData<PreventionResponse> = MutableLiveData()  //read, write
    val getPrevention: LiveData<PreventionResponse> = _getPrevention //read
    private val getPreventionService = ServicePool.getPrevention

    // Server interaction
    fun getPrevention() {
        if (accessToken != null) {
            getPreventionService.getPrevention(accessToken).enqueue(object : retrofit2.Callback<PreventionResponse> {
                override fun onResponse(call: Call<PreventionResponse>, response: Response<PreventionResponse>) {
                    if (response.isSuccessful) {
                        _getPrevention.value = response?.body()
                        Log.d("success getPrevention", _getPrevention.value.toString())
                    } else {
                        Log.d("error getPrevention", "Failed response")
                    }
                }

                override fun onFailure(call: Call<PreventionResponse>, t: Throwable) {
                    t.message?.let { Log.d("error getPrevention", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}