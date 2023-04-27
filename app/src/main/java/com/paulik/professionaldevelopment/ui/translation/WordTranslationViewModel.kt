package com.paulik.professionaldevelopment.ui.translation

import androidx.lifecycle.LiveData
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.WordTranslationInteractorImpl
import com.paulik.professionaldevelopment.ui.root.BaseViewModel
import com.paulik.professionaldevelopment.ui.utils.parseOnlineSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordTranslationViewModel(
    private val interactor: WordTranslationInteractorImpl
) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        /** Загрузка контента */
        cancelJob()
        /** Завершаем все работы с карутинами. Для того чтобы небыло некорректного
        отображения данных. В Случае, например, если запрос по слову будет долго
        выполнятся, а мы отправим еще один запрос, то в теории может сложится так,
        что пользователю придет не корректный ответ и отобразится на экране.  */

        /** Новая задача с карутинами. launch вызывается на том потоке на котором указ в скоопе
         * (viewModelCoroutineScope), то-есть в mainTread */
        viewModelCoroutineScope.launch {
            startInteractor(word, isOnline)
        }
    }

    /** Запустили карутину. В нутри карутины можем вызывать со спен функции. эта та функция которая
     * может прерыватся.*/
    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        /** IO - это Tread на котором запускается соответствующий контекст */
        withContext(Dispatchers.IO) {
            /** postValue используем потому что он может автоматически вернуть результат на mainTread.
             * То-есть нет необходимости переключатся с IO Tread обратно на mainTread*/
            _mutableLiveData.postValue(parseOnlineSearchResults(interactor.getData(word, isOnline)))
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}