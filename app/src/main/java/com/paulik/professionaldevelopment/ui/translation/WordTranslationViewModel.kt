package com.paulik.professionaldevelopment.ui.translation

import androidx.lifecycle.LiveData
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.RepositoryImpl
import com.paulik.professionaldevelopment.data.WordTranslationInteractorImpl
import com.paulik.professionaldevelopment.data.room.DataSourceLocal
import com.paulik.professionaldevelopment.di.DataSourceRemote
import com.paulik.professionaldevelopment.ui.root.BaseViewModel
import io.reactivex.observers.DisposableObserver

class WordTranslationViewModel(
    private val interactor: WordTranslationInteractorImpl = WordTranslationInteractorImpl(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    )
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
