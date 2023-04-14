package com.paulik.professionaldevelopment.ui.translation

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.RepositoryImpl
import com.paulik.professionaldevelopment.data.room.DataSourceLocal
import com.paulik.professionaldevelopment.data.rx.SchedulerProvider
import com.paulik.professionaldevelopment.di.DataSourceRemote
import com.paulik.professionaldevelopment.domain.Presenter
import com.paulik.professionaldevelopment.domain.repo.Interactor
import com.paulik.professionaldevelopment.domain.rx.ISchedulerProvider
import com.paulik.professionaldevelopment.ui.root.ViewApp
import io.reactivex.disposables.CompositeDisposable

class WordTranslationPresenterImpl<T : AppState, V : ViewApp>(
    private val interactor: Interactor<AppState> = WordTranslationInteractor( // todo Изменение
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val schedulerProvider: ISchedulerProvider = SchedulerProvider()  // todo Изменение
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()  // todo compositeDisposable освобождает все что было добавленно в него (из Rx). Можно использовать как clear так и dispose
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                // todo как вариант написания
                .subscribe({
                    currentView?.renderData(it)
                }, {
                    currentView?.renderData(AppState.Error(it))
                })
        )
    }
}