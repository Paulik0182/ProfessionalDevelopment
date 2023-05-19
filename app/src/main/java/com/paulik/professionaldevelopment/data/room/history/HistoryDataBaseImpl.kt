package com.paulik.professionaldevelopment.data.room.history

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.entity.HistoryEntity
import com.paulik.professionaldevelopment.domain.source.DataSourceHistory
import com.paulik.professionaldevelopment.ui.utils.convertDataModelSuccessToEntity
import com.paulik.professionaldevelopment.ui.utils.mapHistoryEntityToSearchResult

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
