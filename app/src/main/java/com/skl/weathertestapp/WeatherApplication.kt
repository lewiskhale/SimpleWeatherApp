package com.skl.weathertestapp

import android.app.Application
import com.skl.weathertestapp.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(allModules)
        }
    }
}