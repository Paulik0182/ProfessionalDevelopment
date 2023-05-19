package com.paulik.professionaldevelopment.data.room.history

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.HistoryRepo
import com.paulik.professionaldevelopment.domain.source.DataSourceHistory

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