package com.paulik.historyscreen

import com.paulik.repository.data.HistoryWordTranslationInteractorImpl
import com.paulik.repository.room.history.HistoryLocalRepoImpl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {

    factory {
        HistoryWordTranslationViewModel(
            get<HistoryWordTranslationInteractorImpl>(),
            get<HistoryLocalRepoImpl>()
        )
    }
}