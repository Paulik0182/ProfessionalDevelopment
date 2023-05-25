package com.paulik.repository.domain.source

import com.paulik.models.AppState

interface DataSourceHistory<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getDataByWord(word: String): T

    suspend fun deleteWord(word: String)
}