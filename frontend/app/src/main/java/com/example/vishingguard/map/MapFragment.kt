package com.example.vishingguard.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.vishingguard.R
import com.example.vishingguard.base.BindingFragment
import com.example.vishingguard.databinding.FragmentMapBinding
import com.example.vishingguard.map.data.RouteAdapter
import com.example.vishingguard.map.data.RouteViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Date

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private val viewModel by viewModels<RouteViewModel>()

    private val arrivedLocationsList = mutableListOf<String>()
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val thresholdDistance = 1000 // meters
    private lateinit var mapList: List<LatLng>
    private lateinit var placeList: List<String>
    private val timeList = mutableListOf<String>()
    private lateinit var routeAdapter: RouteAdapter
    private lateinit var mMap: GoogleMap

    private companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun initView() {
        viewModel.postRoute()

        // Observe Route data
        viewModel.postRoute.observe(viewLifecycleOwner) { response ->
            if (response.data.isNotEmpty()) {
                // Populate mapList with route data
                mapList = response.data.map { LatLng(it.x, it.y) }
                placeList = response.data.map { it.institution }

                // Check if location permission is granted
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Get last known location and add marker to map
                    fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                        location?.let {
                            val currentLatLng = LatLng(it.latitude, it.longitude)
                            mMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                        }
                    }
                } else {
                    // Show toast message if location permission is not granted
                    showToast("Location permission is required.")
                }
            }
        }

        routeAdapter = RouteAdapter(timeList, arrivedLocationsList) // Initialize adapter instance
        binding.rvRoute.adapter = routeAdapter // Set adapter
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the map fragment and set the callback
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        requestLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        removeLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // Location update interval (milliseconds)
            fastestInterval = 5000 // Fastest location update interval (milliseconds)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { location ->
                    // Check for arrival when location is updated
                    checkArrival(location)
                }
            }
        }

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            requestLocationPermission()
        }
    }
    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide explanation for location permission
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates()
            } else {
                showToast("Location permission is required.")
                // Request location permission only if it's denied
                requestLocationPermission()
            }
        }
    }

    private fun removeLocationUpdates() {
        // Stop location updates
    }

    private fun checkArrival(location: Location) {
        for ((index, targetLocation) in mapList.withIndex()) {
            val distance = calculateDistance(location.latitude, location.longitude, targetLocation.latitude, targetLocation.longitude)
            if (distance < thresholdDistance && !arrivedLocationsList.contains(placeList[index])) {

                showToast("Arrived!")
                binding.rvRoute.visibility = View.VISIBLE
                binding.mapContainer.visibility = View.VISIBLE
                binding.backGroundRoad.visibility = View.GONE
                binding.imgNoRoad.visibility = View.GONE
                binding.tvNoRoad.visibility = View.GONE

                // Get current date and time
                val arrival = SimpleDateFormat("MM-dd\nHH:mm").format(Date())
                timeList.add(arrival)

                arrivedLocationsList.add(placeList[index]) // Add the name of the arrived location to the list
                routeAdapter.notifyItemInserted(arrivedLocationsList.size - 1) // Update RecyclerView

                if(timeList.size >= 3){
                    Log.d("timeList", "${timeList.size}")
                    binding.imgResult.setImageResource(R.drawable.img_map_risky)
                    binding.tvResult.text = getString(R.string.tv_map_risky)
                }

                // Add marker to map when arrival is detected
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                return
            }
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Get last known location and add marker to map
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    mMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

                    // Log the current location
                    Log.d("postRoute", "Current location: $currentLatLng")
                }
            }
        } else {
            // Show toast message if location permission is not granted
            showToast("Location permission is required.")
        }
    }
}