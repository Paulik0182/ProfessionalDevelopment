package com.paulik.professionaldevelopment.ui.history

import androidx.lifecycle.LiveData
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.HistoryWordTranslationInteractorImpl
import com.paulik.professionaldevelopment.data.room.RepositoryLocalImpl
import com.paulik.professionaldevelopment.ui.root.BaseViewModel
import com.paulik.professionaldevelopment.ui.utils.parseLocalSearchResults
import kotlinx.coroutines.launch

class HistoryWordTranslationViewModel(
    private val interactor: HistoryWordTranslationInteractorImpl,
    private val repositoryLocal: RepositoryLocalImpl
) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word, isOnline)
        }
    }

    fun searchData(word: String) {
        viewModelCoroutineScope.launch {
            _mutableLiveData.postValue(AppState.Loading(null))
            try {
                val data = repositoryLocal.getLocalData(word)
                _mutableLiveData.postValue(AppState.Success(data))
            } catch (e: Exception) {
                e.printStackTrace()
                _mutableLiveData.postValue(AppState.Error(e))
            }
        }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
//        val data: List<DataEntity> = if (isOnline) {
//            interactor.getData(word, isOnline)
//        } else {
//            localData.getDataByWord(word)
//        }
//        _mutableLiveData.postValue(parseLocalSearchResults(data))

        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value =
            AppState.Success(null) // Установите вид в исходное состояние в onStop
        super.onCleared()
    }
}
