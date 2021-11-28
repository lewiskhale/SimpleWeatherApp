package com.skl.weathertestapp.presentation.screen.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skl.weathertestapp.data.repository.ForecastRepository
import kotlinx.coroutines.launch

class HomeScreenVM(private val repo: ForecastRepository): ViewModel() {

    fun getWeather(){
        viewModelScope.launch {
            val forecast = repo.getForecast()
            if (forecast != null) {
                Log.d("TAG", "getWeather: The current temp is: ${forecast.current.current_temp}")
            }
            else{
                Log.d("TAG", "getWeather: Was null")
            }
        }
    }


}