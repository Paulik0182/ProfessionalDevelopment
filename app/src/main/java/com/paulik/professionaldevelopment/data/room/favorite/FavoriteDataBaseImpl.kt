package com.paulik.professionaldevelopment.data.room.favorite

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSourceFavorite
import com.paulik.professionaldevelopment.ui.utils.mapFavoriteEntityToSearchResult

class FavoriteDataBaseImpl(
    private val favoriteDao: FavoriteDao
) : DataSourceFavorite<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        return mapFavoriteEntityToSearchResult(favoriteDao.all())
    }

    override suspend fun addToFavorites(word: String, isFavorite: Boolean) {
        val favorite = Favorite(word, null, isFavorite)
        favoriteDao.insertFavorite((favorite))
    }

    override suspend fun removeFromFavorite(word: String, isFavorite: Boolean) {
        favoriteDao.deleteFavorite(word)
    }

    override suspend fun getFavoriteEntities(): List<Favorite> = favoriteDao.getFavoriteEntities()
}
