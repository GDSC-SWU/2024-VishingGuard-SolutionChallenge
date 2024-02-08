package com.example.vishingguard.login

import com.example.vishingguard.R
import com.example.vishingguard.base.BindingActivity
import com.example.vishingguard.databinding.ActivityLoginBinding

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun initView() {
        replaceFragment()
    }

    fun replaceFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_login, SelectFragment())
            .addToBackStack(null)
            .commit()
    }
}