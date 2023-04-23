package com.paulik.professionaldevelopment

import android.app.Application
import com.paulik.professionaldevelopment.di.application
import com.paulik.professionaldevelopment.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //подключение Koin
        startKoin {
            androidLogger()
            androidContext(this@TranslatorApp)
            modules(listOf(application, mainScreen))
        }
    }
}