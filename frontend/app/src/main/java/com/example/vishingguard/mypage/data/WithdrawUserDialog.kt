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
import com.example.vishingguard.databinding.WithdrawUserDialogBinding
import com.example.vishingguard.login.LoginActivity

class WithdrawUserDialog(context: Context, private val password: String) : Dialog(context) {

    private lateinit var binding: WithdrawUserDialogBinding
    private val viewModel = MyPageViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.withdraw_user_dialog, null, false)
        setContentView(binding.root)

        // Make the background transparent
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnNo.setOnClickListener {
            dismiss()
        }

        binding.btnYes.setOnClickListener {
            withdrawUser()
            dismiss()
        }
    }

    // Withdraw the user
    private fun withdrawUser() {
        val withdrawUserRequest = WithdrawUserRequest(password = password)
        viewModel.withdrawUser(withdrawUserRequest)

        viewModel.withdrawUser.observe(context as LifecycleOwner) { response ->
            if (response.status == 200) {
                // Redirect to the login screen after successful withdrawal
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                Toast.makeText(context, "Your withdrawal has been completed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}