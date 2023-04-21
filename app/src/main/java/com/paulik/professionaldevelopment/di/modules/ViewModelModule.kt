package com.paulik.professionaldevelopment.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paulik.professionaldevelopment.ui.translation.WordTranslationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WordTranslationViewModel::class)
    protected abstract fun wordTranslationViewModel(
        wordTranslationViewModel: WordTranslationViewModel
    ): ViewModel
}