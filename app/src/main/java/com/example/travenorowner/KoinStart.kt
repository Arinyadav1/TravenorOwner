package com.example.travenorowner

import android.app.Application
import com.example.travenorowner.data.repositoryModule
import com.example.travenorowner.features.viewModelModule
import com.example.travenorowner.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinStart : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@KoinStart)
            printLogger(Level.ERROR)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}