package com.paulik.historyscreen

import com.paulik.repository.data.HistoryWordTranslationInteractorImpl
import com.paulik.repository.data.room.history.HistoryLocalRepoImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(historyScreen)
}

val historyScreen = module {

    scope(named<HistoryWordTranslationFragment>()) {
        scoped {
            HistoryWordTranslationInteractorImpl(
                get()
            )
        }
        viewModel {
            HistoryWordTranslationViewModel(
                get<HistoryWordTranslationInteractorImpl>(),
                get<HistoryLocalRepoImpl>()
            )
        }
    }
}