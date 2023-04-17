package com.paulik.professionaldevelopment.ui.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.Presenter

abstract class BaseFragment<T : AppState> : Fragment(), ViewApp {

    protected lateinit var presenter: Presenter<T, ViewApp>

    protected abstract fun createPresenter(): Presenter<T, ViewApp>

    abstract override fun renderData(appState: AppState)

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}