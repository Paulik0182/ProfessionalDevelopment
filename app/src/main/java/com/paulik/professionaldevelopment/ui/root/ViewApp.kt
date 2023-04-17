package com.paulik.professionaldevelopment.ui.root

import com.paulik.professionaldevelopment.AppState

/**
 * Абстрагируем наше View от конкретной реализацией
 * Dependency Inversion Principle (Принцип инверсии зависимостей)
 */

interface ViewApp {

    fun renderData(appState: AppState)
}
