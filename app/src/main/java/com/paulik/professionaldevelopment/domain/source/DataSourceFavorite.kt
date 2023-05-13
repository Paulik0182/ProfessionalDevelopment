package com.paulik.professionaldevelopment.domain.source

import com.paulik.professionaldevelopment.domain.entity.FavoriteEntity

interface DataSourceFavorite<T> : DataSource<T> {

    suspend fun addToFavorites(word: String, isFavorite: Boolean)

    suspend fun removeFromFavorite(word: String, isFavorite: Boolean)

    suspend fun getFavoriteEntities(): List<FavoriteEntity>

    suspend fun deleteWord(word: String)
}