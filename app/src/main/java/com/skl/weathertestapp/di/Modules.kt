package com.skl.weathertestapp.di

import com.skl.weathertestapp.data.datasources.network.mapping.ConditionMapper
import com.skl.weathertestapp.data.datasources.network.mapping.CurrentMapper
import com.skl.weathertestapp.data.datasources.network.mapping.ForecastMapper
import com.skl.weathertestapp.data.datasources.network.mapping.WeeklyForecastMapper
import com.skl.weathertestapp.data.datasources.network.service.ForecastService
import com.skl.weathertestapp.data.datasources.network.service.ForecastServiceImpl
import com.skl.weathertestapp.data.repository.ForecastRepository
import com.skl.weathertestapp.data.repository.ForecastRepositoryImpl
import com.skl.weathertestapp.presentation.screen.homescreen.HomeScreenVM
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val apiModule = module {

    //ktor client
    single<ForecastService> {
        ForecastServiceImpl(
            HttpClient(Android) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(JsonFeature) {
                serializer = GsonSerializer {
                    setPrettyPrinting()
                    disableHtmlEscaping()
                }
            }
        },
        ForecastMapper(
            CurrentMapper(
                ConditionMapper()
            ),
            WeeklyForecastMapper(
                ConditionMapper()
            )
            )
        )
    }
}

val repositoryModule = module {
    single<ForecastRepository> { ForecastRepositoryImpl(get()) }
}

val HomeScreenVMModule = module {
    viewModel { HomeScreenVM(get()) }
}

val allModules: List<Module> = listOf(
    apiModule,
    repositoryModule,
    HomeScreenVMModule
)