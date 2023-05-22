package com.paulik.repository.domain.source

import com.paulik.models.entity.FavoriteEntity

interface DataSourceFavorite<T> : DataSource<T> {

    suspend fun addToFavorites(word: String, isFavorite: Boolean)

    suspend fun removeFromFavorite(word: String, isFavorite: Boolean)

    suspend fun getFavoriteEntities(): List<FavoriteEntity>

    suspend fun getFavoriteWord(word: String): List<FavoriteEntity>

    suspend fun deleteWord(word: String)
}