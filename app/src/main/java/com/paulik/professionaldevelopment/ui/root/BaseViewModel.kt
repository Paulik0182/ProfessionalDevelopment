package com.paulik.professionaldevelopment.ui.root

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T : AppState>(
    // Наблюдает за фрагментом
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    // Для завершения всех подписок
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    val viewState: LiveData<T> =
        liveDataForViewToObserve// для постоянного наблюдения за изменениями

    // Возвращает не изменяемую LiveData
    abstract fun getData(word: String, isOnline: Boolean)

    // Вызывается тогда когда ViewModel будет уничтожена
    override fun onCleared() {
        compositeDisposable.clear()
    }
}