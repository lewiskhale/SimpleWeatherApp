package com.skl.weathertestapp.presentation.screen.homescreen

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_NO_POWER
import com.google.android.gms.tasks.CancellationTokenSource
import com.skl.weathertestapp.databinding.FragmentHomeScreenBinding
import com.skl.weathertestapp.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import java.util.jar.Manifest

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>() {

    override fun getViewBinding(): FragmentHomeScreenBinding = FragmentHomeScreenBinding.inflate(layoutInflater)

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val viewmodel:HomeScreenVM by sharedViewModel()
    private val presenter: HomeScreenPresenter by sharedViewModel{
        parametersOf(viewmodel)
    }
    private val cts = CancellationTokenSource()

    private val locationCallback = object: LocationCallback(){
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.forEach { location ->
                if(location != null){
                    Log.d("TAG", "onResultCallback: the latitude: ${location.latitude}latitude and longitude: ${location.longitude}")
                    stopLocationUpdates()
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.button.setOnClickListener {
            getLocation()
        }
        //presenter.getWeather()
        subscribeObserver()
    }

    private fun subscribeObserver(){
        viewmodel.forecast.observe(viewLifecycleOwner, {
            //todo stuff happens here
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        Log.d("TAG", "getLocation: Permissions is: ${hasPermissions()}")
        val fine_loc = ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse_loc = ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        Log.d("TAG", "getLocation: Access fine location: $fine_loc and Coarse Loc: $coarse_loc")

        if(hasPermissions()){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
                if(location == null){
                    val locationRequest = LocationRequest.create().apply {
                        setPriority(PRIORITY_NO_POWER)
                        setInterval(20 * 1000)
                    }
                    startLocationUpdates(locationRequest)
                }
                else{
                    val latitude = location?.let {it->
                        it.latitude
                    } ?: "location is null"

                    val longitude = location?.let {it->
                        it.longitude
                    } ?: "location is null"

                    Log.d("TAG", "onViewCreated: the latitude: $latitude and longitude: $longitude")
                }
            }
        }
        else{
            Log.d("TAG", "getLocation: Need to have Location in order to access weather for region")
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationRequest: LocationRequest) {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun hasPermissions(): Boolean =
        presenter.getPermission()

}