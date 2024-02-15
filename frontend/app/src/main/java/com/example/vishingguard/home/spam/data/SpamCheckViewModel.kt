package com.example.vishingguard.home.spam.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class SpamCheckViewModel : ViewModel() {

    // userData
    val accessToken = UserData.getToken()

    // SpamCheck LiveData
    private val _postSpamCheck: MutableLiveData<SpamCheckResponse> = MutableLiveData()  //read, write
    val postSpamCheck: LiveData<SpamCheckResponse> = _postSpamCheck //read
    private val postSpamCheckService = ServicePool.postSpamCheck

    // Server interaction
    fun postSpamCheck(spamCheckRequest: SpamCheckRequest) {
        if (accessToken != null) {
            postSpamCheckService.postSpamCheck(accessToken, spamCheckRequest).enqueue(object : retrofit2.Callback<SpamCheckResponse> {
                override fun onResponse(call: Call<SpamCheckResponse>, response: Response<SpamCheckResponse>) {
                    if (response.isSuccessful) {
                        _postSpamCheck.value = response.body()
                        Log.d("success postSpamCheck", _postSpamCheck.value.toString())
                    } else {
                        Log.d("error postSpamCheck", "Failed response")
                    }
                }

                override fun onFailure(call: Call<SpamCheckResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postSpamCheck", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}