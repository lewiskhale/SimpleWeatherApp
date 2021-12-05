package com.skl.weathertestapp.presentation.screen.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import com.skl.weathertestapp.domain.Forecast

class HomeScreenVM: ViewModel() {

    val forecast: MutableLiveData<Forecast> = MutableLiveData()

    val isLoading: StateFlow<Boolean> = MutableStateFlow(false)

    var _permissionGranted: MutableLiveData<Boolean> = MutableLiveData(false)

    val permissionGranted: LiveData<Boolean> get() = _permissionGranted!!
}