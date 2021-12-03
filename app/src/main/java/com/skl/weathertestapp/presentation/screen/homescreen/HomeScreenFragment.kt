package com.skl.weathertestapp.presentation.screen.homescreen

import android.os.Bundle
import android.view.View
import com.skl.weathertestapp.databinding.FragmentHomeScreenBinding
import com.skl.weathertestapp.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>() {

    override fun getViewBinding(): FragmentHomeScreenBinding = FragmentHomeScreenBinding.inflate(layoutInflater)

    private val viewmodel:HomeScreenVM by viewModel()
    private val presenter: HomeScreenPresenter by viewModel{
        parametersOf(viewmodel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getWeather()
        subscribeObserver()
    }

    private fun subscribeObserver(){
        viewmodel.forecast.observe(viewLifecycleOwner, {
            binding.text.text = it.current.current_temp.toString()
        })
    }

}