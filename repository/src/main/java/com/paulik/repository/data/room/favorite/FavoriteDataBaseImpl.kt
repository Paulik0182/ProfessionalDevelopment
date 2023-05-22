package com.paulik.repository.room.favorite

import com.paulik.models.entity.DataEntity
import com.paulik.models.entity.FavoriteEntity
import com.paulik.repository.domain.source.DataSourceFavorite
import com.paulik.repository.mapFavoriteEntityToSearchResult

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

    override suspend fun getFavoriteWord(word: String): List<FavoriteEntity> {
        val formattedWord = word.trim().uppercase()
        return favoriteDao.all().filter {
            it.word.trim().uppercase().contains(formattedWord)
        }
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
