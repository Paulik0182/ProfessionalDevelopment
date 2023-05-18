package com.paulik.professionaldevelopment

import android.app.Application
import com.paulik.professionaldevelopment.di.application
import com.paulik.professionaldevelopment.di.stopwatchScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    application,
                    stopwatchScreen
                )
            )
        }
    }
}