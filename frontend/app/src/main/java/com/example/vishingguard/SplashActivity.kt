package com.example.vishingguard

import android.content.Intent
import com.bumptech.glide.Glide
import com.example.vishingguard.base.BindingActivity
import com.example.vishingguard.databinding.ActivitySplashBinding
import com.example.vishingguard.login.LoginActivity

class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun initView() {

        Glide.with(this).load(R.raw.splash_logo).into(binding.imgSplash)

        // 3초 후에 로그인 액티비티로 이동
        binding.imgSplash.postDelayed({
            val signUpIntent = Intent(this, LoginActivity::class.java)
            signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(signUpIntent)
            finish()
        }, 3000)
    }
}