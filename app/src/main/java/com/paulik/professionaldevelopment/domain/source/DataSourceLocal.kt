package com.paulik.professionaldevelopment.domain.source

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteEntity

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getDataByWord(word: String): T

    suspend fun saveWord(word: String)

    suspend fun deleteWord(word: String)

    suspend fun addToFavorites(word: String)

    suspend fun removeFromFavorite(word: String)

    suspend fun getFavoriteEntities(): List<FavoriteEntity>
}