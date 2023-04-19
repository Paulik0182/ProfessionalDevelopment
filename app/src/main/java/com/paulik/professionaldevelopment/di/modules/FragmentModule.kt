package com.paulik.professionaldevelopment.di.modules

import com.paulik.professionaldevelopment.ui.translation.WordTranslationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**По факту здесь сказано Daggers, что можно инжектить во фрагмент или активити.
 * Фактически данный код копируется из проекта в проект.*/

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeWordTranslationFragment(): WordTranslationFragment
}