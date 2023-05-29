package com.paulik.repository.data.room.history

import com.paulik.models.AppState
import com.paulik.models.entity.DataEntity
import com.paulik.models.entity.HistoryEntity
import com.paulik.repository.convertDataModelSuccessToEntity
import com.paulik.repository.domain.source.DataSourceHistory
import com.paulik.repository.mapHistoryEntityToSearchResult

class HistoryDataBaseImpl(
    private val historyDao: HistoryDao
) : DataSourceHistory<List<DataEntity>> {

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
            it.word.trim().uppercase().contains(formattedWord)
        })
    }

    override suspend fun deleteWord(word: String) {
        historyDao.delete(
            HistoryEntity(
                word = word,
                description = null
            )
        )
    }
}
