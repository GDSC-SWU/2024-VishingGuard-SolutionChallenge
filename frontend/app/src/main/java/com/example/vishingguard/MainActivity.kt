package com.example.vishingguard

import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vishingguard.base.BindingActivity
import com.example.vishingguard.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        setNavigation()
    }

    private fun setNavigation() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()

        navController?.let {
            binding.botNavMain.setupWithNavController(navController)
            binding.botNavMain.itemIconTintList = null // 아이콘 테마색으로 변경 방지
        }
    }
}