package com.skl.weathertestapp.presentation.screen.homescreen

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationRequest.PRIORITY_NO_POWER
import com.google.android.gms.tasks.CancellationTokenSource
import com.skl.weathertestapp.databinding.FragmentHomeScreenBinding
import com.skl.weathertestapp.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import java.util.jar.Manifest

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>() {

    override fun getViewBinding(): FragmentHomeScreenBinding = FragmentHomeScreenBinding.inflate(layoutInflater)

    private var requestingLocation: Boolean = false

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewmodel:HomeScreenVM by sharedViewModel()

    private val presenter: HomeScreenPresenter by sharedViewModel{
        parametersOf(viewmodel)
    }

    private val locationCallback = object: LocationCallback(){
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        requestingLocation = presenter.getPermission()
        binding.button.setOnClickListener {
            getLocation()
        }
        subscribeObservers()
    }

    private fun subscribeObservers(){
        viewmodel.forecast.observe(viewLifecycleOwner) { forecast->
            binding.currentTempText.text = forecast.current.current_temp.toString()
        }

        viewmodel.permissionGranted.observe(viewLifecycleOwner){
            requestingLocation = it
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        if(requestingLocation){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
                startLocationUpdates()
                presenter.getWeather(location.latitude, location.longitude)
            }
        }
        else{
            Log.d("TAG", "getLocation: Need to have Location in order to access weather for region")
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {

        Log.d("TAG", "startLocationUpdates: has started")
        val locationRequest = LocationRequest.create().apply {
            setPriority(PRIORITY_HIGH_ACCURACY)
            setInterval(2000)
            setFastestInterval(1000)
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        Log.d("TAG", "stopLocationUpdates: has started")
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if(requestingLocation)
            startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}