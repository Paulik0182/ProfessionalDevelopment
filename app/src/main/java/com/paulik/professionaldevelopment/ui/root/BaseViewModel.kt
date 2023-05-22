package com.paulik.professionaldevelopment.ui.root

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulik.models.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val viewModelCoroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.Main
                /**Указывается на какой поток длжены вернуть результат */
                /** Для то чтобы. если задача упадет, то это не вызывала проблем у всех других задач*/
                + SupervisorJob()
                /** Обработка ошибок*/
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    /** В данном методе завершаем работу со всеми карутинвми*/
    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun getData(word: String, isOnline: Boolean)

    abstract fun handleError(error: Throwable)
}