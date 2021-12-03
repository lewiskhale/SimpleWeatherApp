package com.skl.weathertestapp.presentation.screen.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skl.weathertestapp.data.repository.ForecastRepository
import com.skl.weathertestapp.domain.Forecast
import kotlinx.coroutines.launch

class HomeScreenVM: ViewModel() {

    val forecast: LiveData<Forecast> = MutableLiveData()

    val isLoading: LiveData<Boolean> = MutableLiveData(false)

}