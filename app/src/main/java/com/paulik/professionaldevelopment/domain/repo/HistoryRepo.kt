package com.paulik.professionaldevelopment.domain.repo

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity

interface HistoryRepo<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getDataByWord(word: String): List<DataEntity>

    suspend fun getLocalData(word: String): List<DataEntity>

    suspend fun deleteWordSearchHistory(word: String)
}
