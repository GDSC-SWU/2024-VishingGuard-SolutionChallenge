package com.example.vishingguard.home

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.community.CommunityFragment
import com.example.vishingguard.databinding.FragmentHomeBinding
import com.example.vishingguard.home.data.BannerAdapter
import com.example.vishingguard.home.data.HomeAdapter
import com.example.vishingguard.home.data.HomeViewModel
import com.example.vishingguard.home.prevention.PreventionFragment
import com.example.vishingguard.home.procedure.ProcedureFragment
import com.example.vishingguard.home.report.ReportFragment
import com.example.vishingguard.home.spam.SpamCheckFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun initView() {
        // Create a list of images for the banner
        val imgList: ArrayList<Any> = arrayListOf()
        imgList.add(R.drawable.img_banner1)
        imgList.add(R.drawable.img_banner2)
        imgList.add(R.drawable.img_banner3)

        // Set up ViewPager for the banner
        binding.viewPagerBanner.offscreenPageLimit = 1 // Load one page in advance
        binding.viewPagerBanner.adapter = BannerAdapter(imgList)
        binding.viewPagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Set up dots indicator for the banner
        binding.dotsIndicator.setViewPager2(binding.viewPagerBanner)

        // Request to fetch posts from the server
        viewModel.postHome()

        // Handle the response from ViewModel
        handlePostsResponse()

        // Move to the phishing report screen
        binding.btnCheck.setOnClickListener {
            navigateToMyPage()
        }

        // Move to the procedure screen
        binding.btnProcedure.setOnClickListener {
            replaceFragment(ProcedureFragment())
        }

        // Move to the report screen
        binding.btnReport.setOnClickListener {
            replaceFragment(ReportFragment())
        }

        // Move to the prevention screen
        binding.btnPrevention.setOnClickListener {
            replaceFragment(PreventionFragment())
        }

        // Move to the search screen
        binding.btnSearch.setOnClickListener {
            replaceFragment(SpamCheckFragment())
        }

        binding.viewRv.setOnClickListener {
            replaceFragment(CommunityFragment())
        }
    }

    // Navigate to the phishing report screen
    private fun handlePostsResponse() {
        // Observe Posts data
        viewModel.postHome.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                // Populate RecyclerView with Vishing reports
                val data = response.data
                val adapter = HomeAdapter(data)
                binding.rvPosts.adapter = adapter

            }
        }
    }

    // Move to the phishing report screen
    private fun navigateToMyPage() {
        val navController = requireActivity().findNavController(R.id.container_main)
        navController.navigate(R.id.navigation_pishing)
    }

    // Replace fragments
    fun replaceFragment(fragment: Fragment){
        requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav_main).visibility = View.GONE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}