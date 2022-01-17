package com.skl.weathertestapp.presentation.screen.homescreen

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.skl.weathertestapp.databinding.FragmentHomeScreenBinding
import com.skl.weathertestapp.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import com.skl.weathertestapp.R
import java.util.*

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>() {

    override fun getViewBinding(): FragmentHomeScreenBinding = FragmentHomeScreenBinding.inflate(layoutInflater)

    private var requestingLocation: Boolean = false
    private var nameOfCity: String = ""

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel:HomeScreenVM by sharedViewModel()

    private val presenter: HomeScreenPresenter by sharedViewModel{
        parametersOf(viewModel)
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
        viewModel.forecast.observe(viewLifecycleOwner) { forecast->
            binding.nameOfCity.text = nameOfCity
            binding.currentTemp.text = resources.getString(R.string.temp_with_decimal, forecast.current.current_temp.toString())
            binding.currentTempMin.text = resources.getString(R.string.temp_with_decimal, forecast.forecast.forecast_list.first().day_of_the_week_info.min.toString())
            binding.currentTempMax.text = resources.getString(R.string.temp_with_decimal, forecast.forecast.forecast_list.first().day_of_the_week_info.max.toString())
            binding.currentTempFeelsLike.text = forecast.current.condition.text //resources.getString(R.string.temp_with_decimal, forecast.forecast.forecast_list.first().day_of_the_week_info.max.toString())
            val uri = Uri.parse(forecast.current.condition.img).buildUpon().scheme("https").build()
            Log.d("TAG", "subscribeObservers: uri is: $uri")
            Glide
                .with(this)
                .load(uri)
                .into(binding.currentConditionIcon)
            val weatherConditionCode = forecast.current.condition.code
            updateBackground(weatherConditionCode)
        }

        viewModel.permissionGranted.observe(viewLifecycleOwner){
            requestingLocation = it
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateBackground(weatherConditionCode: Int) {
        when (weatherConditionCode) {
            1000 -> {
                binding.weatherImageBg.setImageDrawable(resources.getDrawable(R.drawable.clear, null))
                binding.weatherWeekForecastList.setBackgroundColor(resources.getColor(R.color.sunny_base, null))
            }
            in 1003..1009 -> {
                binding.weatherImageBg.setImageDrawable(resources.getDrawable(R.drawable.forest_cloudy, null))
                binding.weatherWeekForecastList.setBackgroundColor(resources.getColor(R.color.cloudy_base, null)) }
            else -> {
                binding.weatherImageBg.setImageDrawable(resources.getDrawable(R.drawable.forest_rainy, null))
                binding.weatherWeekForecastList.setBackgroundColor(resources.getColor(R.color.rainy_base, null)) }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        if(requestingLocation){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
                startLocationUpdates()
                try {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val fakeAddress = Address(Locale.getDefault())
                    fakeAddress.locality = "Cape Town"
                    val addresses: List<Address> = if(list.isNotEmpty()) list else listOf(fakeAddress)
                    nameOfCity = addresses.first().locality
                }catch (e: Exception){
                    throw Exception("There was an exception: $e")
                }
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
            priority = PRIORITY_HIGH_ACCURACY
            interval = 2000
            fastestInterval = 1000
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