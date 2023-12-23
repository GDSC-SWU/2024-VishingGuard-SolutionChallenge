package com.example.vishingguard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.vishingguard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<SmishingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        processCommand(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        processCommand(intent)
    }

    private fun processCommand(intent: Intent?) {
        if (intent != null) {
            val content = intent.getStringExtra("content")
            binding.tvMessage.text = content.toString()

            //서버에 값 전달
            viewModel.postSmishing(content.toString())
            Log.d("post contents", content.toString())
        }
    }
}