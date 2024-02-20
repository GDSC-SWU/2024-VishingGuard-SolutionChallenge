package com.example.vishingguard.home

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
    private val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 123

    override fun initView() {
        // Create a list of images for the banner
        val imgList: ArrayList<Any> = arrayListOf()
        imgList.add(R.drawable.img_banner1)
        imgList.add(R.drawable.img_banner2)
        imgList.add(R.drawable.img_banner3)

        // Set up ViewPager for the banner
        val bannerAdapter = BannerAdapter(imgList, object : BannerAdapter.OnBannerClickListener {
            override fun onBannerClick(position: Int) {
                // Perform desired actions based on the clicked banner position
                getFssFile(position)
            }
        })
        binding.viewPagerBanner.adapter = bannerAdapter
        binding.viewPagerBanner.offscreenPageLimit = 1 // Load one page in advance
        binding.viewPagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Set up dots indicator for the banner
        binding.dotsIndicator.setViewPager2(binding.viewPagerBanner)

        // Request storage permission
        requestStoragePermission()

        // Request to fetch posts from the server
        viewModel.postHome()

        // Handle the response from ViewModel
        handlePostsResponse()

        // Voice Phishing Related File Downloaded
        binding.viewPagerBanner.setOnClickListener {
            val currentPosition = binding.viewPagerBanner.currentItem
            getFssFile(currentPosition)
        }

        // Move to the phishing report screen
        binding.btnCheck.setOnClickListener {
            navigateToMyPage()
        }

        // Set click listeners for navigation buttons
        binding.btnProcedure.setOnClickListener {
            replaceFragment(ProcedureFragment())
        }

        binding.btnReport.setOnClickListener {
            replaceFragment(ReportFragment())
        }

        binding.btnPrevention.setOnClickListener {
            replaceFragment(PreventionFragment())
        }

        // Move to the search screen
        binding.btnSearch.setOnClickListener {
            replaceFragment(SpamCheckFragment())
        }

        // Move to the community screen
        binding.viewRv.setOnClickListener {
            replaceFragment(CommunityFragment())
        }
    }

    // Function to fetch FSS file based on the provided number
    private fun getFssFile(number : Int){
        viewModel.getFss(requireContext(),number)
        Toast.makeText(requireContext(), R.string.tv_down, Toast.LENGTH_SHORT).show()
        Log.d("getFss", "getFss : ${number}" )
    }

    // Function to handle response from fetching posts
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

    // Function to navigate to the phishing report screen
    private fun navigateToMyPage() {
        val navController = requireActivity().findNavController(R.id.container_main)
        navController.navigate(R.id.navigation_pishing)
    }

    // Function to replace fragments
    fun replaceFragment(fragment: Fragment){
        requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav_main).visibility = View.GONE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Function to request storage permission
    private fun requestStoragePermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Log.d("Permission", "WRITE_EXTERNAL_STORAGE permission granted")
            } else {
                // Permission denied
                Log.d("Permission", "WRITE_EXTERNAL_STORAGE permission denied")
                // In case of permission denial, you can request permission again from the user.
            }
        }
    }
}