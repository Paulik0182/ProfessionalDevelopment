package com.paulik.professionaldevelopment.domain

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.ui.root.ViewApp

interface Presenter<T : AppState, V : ViewApp> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
