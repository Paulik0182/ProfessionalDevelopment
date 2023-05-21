package com.paulik.professionaldevelopment.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteDataBaseImpl
import com.paulik.professionaldevelopment.domain.entity.FavoriteEntity
import com.paulik.utils.mutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class FavoriteWordViewModel(
    private val favoriteLocalRepo: FavoriteDataBaseImpl
) : ViewModel() {

    val selectedDetailsWordLiveData: LiveData<FavoriteEntity> = MutableLiveData()
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

    fun onWordClick(favoriteEntity: FavoriteEntity) {
        (selectedDetailsWordLiveData as MutableLiveData).value = favoriteEntity
    }

    fun searchData(word: String) {
        viewModelCoroutineScope.launch {
            try {
                val data = favoriteLocalRepo.getFavoriteWord(word)
                favoriteEntityLiveData.mutable().postValue(data)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("@@@", "FavoriteWordViewModel -> searchData: $word  (Exception)")
            }
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