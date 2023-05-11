package com.paulik.professionaldevelopment.data.room.history

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.room.favorite.FavoriteEntity
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSourceLocal
import com.paulik.professionaldevelopment.ui.utils.convertDataModelSuccessToEntity
import com.paulik.professionaldevelopment.ui.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImpl(
    private val historyDao: HistoryDao
) : DataSourceLocal<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }

    override suspend fun getDataByWord(word: String): List<DataEntity> {
        val formattedWord = word.trim().uppercase()
        return mapHistoryEntityToSearchResult(historyDao.all().filter {
            it.word.trim().uppercase() == formattedWord
        })

//        return historyDao.getDataByWord("%$word%") // Искомое слово оборачиваем в %
    }

    override suspend fun saveWord(word: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun deleteWord(word: String) {
        historyDao.delete(
            HistoryEntity(
                word = word,
                description = null
            )
        )
    }

    override suspend fun addToFavorites(word: String) {
        val favorite = FavoriteEntity(word, true)
        historyDao.insertFavorite((favorite))
    }

    override suspend fun removeFromFavorite(word: String) {
        historyDao.deleteFavorite(word)
    }

    override suspend fun getFavoriteEntities(): List<FavoriteEntity> =
        historyDao.getFavoriteEntities()
}
