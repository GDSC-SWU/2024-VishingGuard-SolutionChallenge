package com.example.vishingguard.home.procedure.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class ProcedureViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()

    // Procedure LiveData
    private val _getProcedure: MutableLiveData<ProcedureResponse> = MutableLiveData()  //read, write
    val getProcedure: LiveData<ProcedureResponse> = _getProcedure //read
    private val getProcedureService = ServicePool.getProcedure

    // Server interaction
    fun getProcedure() {
        if (accessToken != null) {
            getProcedureService.getProcedure(accessToken).enqueue(object : retrofit2.Callback<ProcedureResponse> {
                override fun onResponse(call: Call<ProcedureResponse>, response: Response<ProcedureResponse>) {
                    if (response.isSuccessful) {
                        _getProcedure.value = response?.body()
                        Log.d("success getProcedure", _getProcedure.value.toString())
                    } else {
                        Log.d("error getProcedure", "Failed response")
                    }
                }

                override fun onFailure(call: Call<ProcedureResponse>, t: Throwable) {
                    t.message?.let { Log.d("error getProcedure", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}