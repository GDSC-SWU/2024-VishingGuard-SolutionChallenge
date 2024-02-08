package com.example.vishingguard

import android.content.Intent
import android.net.Uri
import com.example.vishingguard.base.BindingActivity
import com.example.vishingguard.databinding.ActivitySplashBinding
import com.example.vishingguard.login.LoginActivity

class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun initView() {
        val videoPath = "android.resource://" + packageName + "/" + R.raw.mp4_splash
        val videoUri = Uri.parse(videoPath)
        binding.videoView.setVideoURI(videoUri)

        // 비디오 재생이 준비되었을 때 재생 시작
        binding.videoView.setOnPreparedListener {
            // 비디오 준비되면 재생
            binding.videoView.start()
        }

        // 비디오 재생이 완료되면 로그인 액티비티로 이동
        binding.videoView.setOnCompletionListener {
            val signUpIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(signUpIntent)
            finish()
        }
    }
}