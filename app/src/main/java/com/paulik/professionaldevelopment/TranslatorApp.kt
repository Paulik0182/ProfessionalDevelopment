package com.paulik.professionaldevelopment

import android.app.Application
import com.paulik.professionaldevelopment.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TranslatorApp : Application(), HasAndroidInjector {

    /**DispatchingAndroidInjector - с помощью него происходит инекции в активить, фрагменты и т.д.
     * Обязательно должен реализовыватся интерфейс HasAndroidInjector (модуль указывается в
     * AppComponent -> AndroidSupportInjectionModule::class.
     * После этого во все активити, фрагменты можно инжектить ->
     * AndroidSupportInjection.inject(this) (инжект во фрагметн),
     * AndroidInjection.inject(this) (инжект в активити)*/

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}