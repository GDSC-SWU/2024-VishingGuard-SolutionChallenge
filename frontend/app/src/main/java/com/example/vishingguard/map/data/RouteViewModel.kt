package com.example.vishingguard.map.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class RouteViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()

    // Route LiveData
    private val _postRoute: MutableLiveData<RouteResponse> = MutableLiveData()  //read, write
    val postRoute: LiveData<RouteResponse> = _postRoute //read
    private val postRouteService = ServicePool.postRoute

    // Server interaction
    fun postRoute() {
        if (accessToken != null) {
            postRouteService.postRoute(accessToken).enqueue(object : retrofit2.Callback<RouteResponse> {
                override fun onResponse(call: Call<RouteResponse>, response: Response<RouteResponse>) {
                    if (response.isSuccessful) {
                        response?.body().also { _postRoute.value = it }
                        Log.d("success postRoute", _postRoute.value.toString())
                    } else {
                        Log.d("error postRoute", "Failed response")
                    }
                }

                override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
                    t.message?.let { Log.d("error postRoute", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}