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

    //private val cts = CancellationTokenSource()
    private var requestingLocation: Boolean = false

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewmodel:HomeScreenVM by sharedViewModel()

    private val presenter: HomeScreenPresenter by sharedViewModel{
        parametersOf(viewmodel)
    }

    private val locationCallback = object: LocationCallback(){
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.forEach { location ->
                if(location != null){
                    stopLocationUpdates()
                }
            }
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
        viewmodel.forecast.observe(viewLifecycleOwner, {
            //todo stuff happens here
        })

        viewmodel.permissionGranted.observe(viewLifecycleOwner){
            requestingLocation = it
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        if(requestingLocation){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
                if(location == null){
                    Log.d("TAG", "getLocation: the location is null")
                    startLocationUpdates()
                }
                else{
                    presenter.getWeather(location.latitude, location.longitude)
                }
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
            setInterval(20 * 1000)
            setFastestInterval(2000)
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

/*
* Log.d("TAG", "getLocation: Permissions is: ${hasPermissions()}")
        val fine_loc = ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse_loc = ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        Log.d("TAG", "getLocation: Access fine location: $fine_loc and Coarse Loc: $coarse_loc")
*/