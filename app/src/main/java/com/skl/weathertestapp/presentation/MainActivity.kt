package com.skl.weathertestapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.skl.weathertestapp.R
import com.skl.weathertestapp.databinding.ActivityMainBinding
import com.skl.weathertestapp.presentation.screen.homescreen.HomeScreenPresenter
import com.skl.weathertestapp.presentation.screen.homescreen.HomeScreenVM
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: HomeScreenVM by viewModel()
    private val presenter: HomeScreenPresenter by viewModel{
        parametersOf(viewModel)
    }


    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ granted ->
        var allGranted = true
        granted.forEach{
            if(it.value == false){
                allGranted = false
            }
        }
        presenter.onPermissionResult(allGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()
        observePermissions()
    }

    private fun checkPermissions(){
        requestPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun observePermissions(){
        lifecycleScope.launchWhenStarted {
            viewModel.permissionGranted.collectLatest { permissionState->
                if (permissionState) {
                    showPermissionsGrantedToast(true)
                } else {
                    showPermissionsGrantedToast(false)
                }
            }
        }
    }

    private fun showPermissionsGrantedToast(granted: Boolean){
        if(granted){
            Toast.makeText(this, "Permission has been granted!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Permission has not been granted!", Toast.LENGTH_SHORT).show()
        }
    }
}