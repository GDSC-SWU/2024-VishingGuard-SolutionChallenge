package com.example.vishingguard.mypage.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class MyPageViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()
    val userId = UserData.getUserId()

    // UpdateUser LiveData
    private val _updateUser: MutableLiveData<UserResponse> = MutableLiveData()  //read, write
    val updateUser: LiveData<UserResponse> = _updateUser //read
    private val updateUserService = ServicePool.updateUser

    // Server interaction
    fun updateUser(updateUserRequest: UpdateUserRequest) {
        if (accessToken != null && userId != null) {
            updateUserService.updateUser(accessToken, userId, updateUserRequest).enqueue(object : retrofit2.Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        _updateUser.value = response.body()
                        Log.d("success updateUser", _updateUser.value.toString())
                    } else {
                        Log.d("error updateUser", "Failed response")
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("error updateUser", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}