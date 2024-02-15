package com.example.vishingguard.home.spam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vishingguard.MainActivity
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentSpamCheckBinding

@Suppress("DEPRECATION")
class SpamCheckFragment : BindingFragment<FragmentSpamCheckBinding>(R.layout.fragment_spam_check) {

    override fun initView() {
        // Move to the search result page
        binding.btnSearch.setOnClickListener{
            sendPhoneNum()
        }

        // Navigate back
        binding.btnBack.setOnClickListener {
            navigateToMainActivity()
        }
    }

    // Send phone number
    private fun sendPhoneNum (){
        if (binding.etPhone.text.toString() != ""){
            val bundle = Bundle()
            bundle.putString("phoneNum", binding.etPhone.text.toString())
            val searchResultFragment = SpamResultFragment()
            searchResultFragment.arguments = bundle

            replaceFragment(searchResultFragment)
        }
        else {
            Toast.makeText(requireContext(), "Number input is missing", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigate to the main activity
    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(0, 0)
    }

    // Replace fragments
    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}