package com.paulik.repository.data.room.history

import com.paulik.models.AppState
import com.paulik.models.entity.DataEntity
import com.paulik.repository.domain.repo.HistoryRepo
import com.paulik.repository.domain.source.DataSourceHistory

class HistoryLocalRepoImpl(
    private val dataSource: DataSourceHistory<List<DataEntity>>
) : HistoryRepo<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getDataByWord(word: String): List<DataEntity> {
        return dataSource.getDataByWord(word)
    }

    override suspend fun getLocalData(word: String): List<DataEntity> {
        return dataSource.getDataByWord(word)
    }

    override suspend fun deleteWordSearchHistory(word: String) {
        dataSource.deleteWord(word)
    }
}