package com.example.vishingguard.community.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vishingguard.ServicePool
import com.example.vishingguard.login.data.UserData
import retrofit2.Call
import retrofit2.Response

class PostsViewModel : ViewModel() {
    // userData
    val accessToken = UserData.getToken()

    // Posts LiveData
    private val _getPosts: MutableLiveData<PostsResponse> = MutableLiveData()  //read, write
    val getPosts: LiveData<PostsResponse> = _getPosts //read
    private val getPostsService = ServicePool.getPosts

    // Server interaction
    fun getPosts() {
        if (accessToken != null) {
            getPostsService.getPosts(accessToken).enqueue(object : retrofit2.Callback<PostsResponse> {
                override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                    if (response.isSuccessful) {
                        _getPosts.value = response.body()
                        Log.d("success getPosts", _getPosts.value.toString())
                    } else {
                        Log.d("error getPosts", "Failed response")
                    }
                }

                override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                    t.message?.let { Log.d("error getPosts", it) } ?: "Failed server communication (no response)"
                }
            })
        }
    }
}