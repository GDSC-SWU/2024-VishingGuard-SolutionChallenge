package com.example.vishingguard.smishing

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.vishingguard.databinding.ActivitySmishingBinding

class SmishingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmishingBinding
    private val viewModel by viewModels<SmishingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmishingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 권한 설정
        requestPermission()

        //서버에 값 전달
        val intent = intent
        processCommand(intent)
    }

    // 권한 설정
    private fun requestPermission() {
        // 버전 체크, 권한 허용했는지 체크
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.RECEIVE_SMS), 0)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        processCommand(intent)
    }

    private fun processCommand(intent: Intent?) {
        if (intent != null) {
            val content = intent.getStringExtra("content")
            if(content != null)
            {
                binding.tvMessage.text = content.toString()
                //서버에 값 전달
                viewModel.postSmishing(content.toString())
                Log.d("post contents", content.toString())
            }
        }
    }
}