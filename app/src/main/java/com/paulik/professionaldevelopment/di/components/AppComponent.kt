package com.paulik.professionaldevelopment.di.components

import android.app.Application
import com.paulik.professionaldevelopment.TranslatorApp
import com.paulik.professionaldevelopment.di.modules.FragmentModule
import com.paulik.professionaldevelopment.di.modules.InteractorModule
import com.paulik.professionaldevelopment.di.modules.RepositoryModule
import com.paulik.professionaldevelopment.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Начало внедрение dagger начинается с этого класса.
 */

@Component(
    modules = [
        /**В модулях описывается то как провадить зависимости */
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        /** @BindsInstance эта аннотация берет аргумент application и сохраняет внутри компонента
         * (AppComponent). То-есть application можно будет инжектить везде где это необходимо.
         * Аннатация радотает только внутри Builder*/
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(englishVocabularyApp: TranslatorApp)
}