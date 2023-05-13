package com.paulik.professionaldevelopment.data.room.favorite

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.entity.FavoriteEntity
import com.paulik.professionaldevelopment.domain.source.DataSourceFavorite
import com.paulik.professionaldevelopment.ui.utils.mapFavoriteEntityToSearchResult

class FavoriteDataBaseImpl(
    private val favoriteDao: FavoriteDao
) : DataSourceFavorite<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        return mapFavoriteEntityToSearchResult(favoriteDao.all())
    }

    override suspend fun addToFavorites(word: String, isFavorite: Boolean) {
        val favoriteEntity = FavoriteEntity(word, null, isFavorite)
        favoriteDao.insertFavorite(favoriteEntity)
    }

    override suspend fun removeFromFavorite(word: String, isFavorite: Boolean) {
        favoriteDao.deleteFavorite(word)
    }

    override suspend fun getFavoriteEntities(): List<FavoriteEntity> =
        favoriteDao.getFavoriteEntities()

    override suspend fun deleteWord(word: String) {
        favoriteDao.delete(
            FavoriteEntity(
                word = word,
                description = null,
                isFavorite = false
            )
        )
    }
}
