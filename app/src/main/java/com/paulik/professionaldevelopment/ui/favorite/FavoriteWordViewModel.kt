package com.paulik.professionaldevelopment.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteDataBaseImpl
import com.paulik.professionaldevelopment.domain.entity.FavoriteEntity
import com.paulik.professionaldevelopment.ui.utils.mutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class FavoriteWordViewModel(
    private val favoriteLocalRepo: FavoriteDataBaseImpl
) : ViewModel() {

    val favoriteEntityLiveData: LiveData<List<FavoriteEntity>> = MutableLiveData()

    private val viewModelCoroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            // todo
        })

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    fun onRefresh() {
        viewModelCoroutineScope.launch {
            val favorites = favoriteLocalRepo.getFavoriteEntities()
            favoriteEntityLiveData.mutable().postValue(favorites)
        }
    }

    fun deleteWord(word: String) {
        viewModelCoroutineScope.launch {
            favoriteLocalRepo.deleteWord(word)
            val updatedList = favoriteLocalRepo.getFavoriteEntities()
            favoriteEntityLiveData.mutable().postValue(updatedList)
        }
    }
}