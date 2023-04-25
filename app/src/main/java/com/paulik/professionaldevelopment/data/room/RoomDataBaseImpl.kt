package com.paulik.professionaldevelopment.data.room

import com.paulik.professionaldevelopment.AppState
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
}
