package com.skl.weathertestapp.presentation.screen.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skl.weathertestapp.data.repository.ForecastRepository
import kotlinx.coroutines.launch

class HomeScreenPresenter
    (private val repo: ForecastRepository,
     private val vm: HomeScreenVM)
    : ViewModel() {

    fun getWeather(){
        viewModelScope.launch {
            val forecastResult = repo.getForecast()
            val forecast = forecastResult.data
            if (forecast != null) {
                Log.d("TAG", "getWeather: The current temp is: ${forecast.current.current_temp}")
            }
            else{
                Log.d("TAG", "getWeather: Was null")
            }
        }
    }

    fun onPermissionResult(wasGranted: Boolean){
        vm._permissionGranted.value = wasGranted
    }

    fun getPermission(): Boolean = vm.permissionGranted.value
}