package com.example.vishingguard.mypage.data

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.example.vishingguard.R
import com.example.vishingguard.databinding.LogoutUserDialogBinding
import com.example.vishingguard.login.LoginActivity

class LogoutUserDialog(context: Context) : Dialog(context) {

    private lateinit var binding: LogoutUserDialogBinding
    private val viewModel = MyPageViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.logout_user_dialog, null, false)
        setContentView(binding.root)

        // Make the background transparent
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnNo.setOnClickListener {
            dismiss()
        }

        binding.btnYes.setOnClickListener {
            logoutUser()
        }
    }

    // Logout the user
    private fun logoutUser() {
        viewModel.logoutUser()

        viewModel.logoutUser.observe(context as LifecycleOwner) { response ->
            if (response.status == 200) {
                // Redirect to the login screen after successful logout
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show()
            }
        }
    }
}