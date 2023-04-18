package com.paulik.professionaldevelopment.ui.root

import androidx.fragment.app.Fragment
import com.paulik.professionaldevelopment.AppState

abstract class BaseFragment<T : AppState> : Fragment() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)
}