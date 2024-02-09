package com.example.vishingguard.login.data

object UserData {
    private var userId: Int? = null
    private var accessToken: String? = null

    fun setLoginResponse(userId: Int, accessToken: String) {
        this.userId = userId
        this.accessToken = "Bearer $accessToken"
    }

    fun getUserId(): Int? {
        return userId
    }

    fun getToken(): String? {
        return accessToken
    }
}