package com.paulik.repository.domain.repo

import com.paulik.models.AppState
import com.paulik.models.entity.DataEntity

interface HistoryRepo<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getDataByWord(word: String): List<DataEntity>

    suspend fun getLocalData(word: String): List<DataEntity>

    suspend fun deleteWordSearchHistory(word: String)
}
