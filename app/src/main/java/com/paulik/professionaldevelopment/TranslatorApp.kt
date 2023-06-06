package com.paulik.professionaldevelopment

import android.app.Application
import com.paulik.historyscreen.historyScreen
import com.paulik.professionaldevelopment.di.application
import com.paulik.professionaldevelopment.di.favoriteScreen
import com.paulik.professionaldevelopment.di.mainScreen
import com.paulik.professionaldevelopment.di.wordDetailsScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //подключение Koin
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                application,
                mainScreen,
                historyScreen,
                wordDetailsScreen,
                favoriteScreen
            )
        }
    }

//    private fun onSplashScreen() {
//        val splashScreen = SplashScreen.installSplashScreen()
//        splashScreen.setKeepVisibleCondition {
//            // Wait for 3 seconds
//            Thread.sleep(3000)
//            true
//        }
//        hideSplashScreen()
//    }
//
//    private fun hideSplashScreen() {
//        SplashScreen.getSplashScreen(this)?.let { splashScreen ->
//            splashScreen.remove()
//        }
//    }
}