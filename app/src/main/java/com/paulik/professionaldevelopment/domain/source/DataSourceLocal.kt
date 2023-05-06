package com.paulik.professionaldevelopment.domain.source

import com.paulik.professionaldevelopment.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getDataByWord(word: String): T

    suspend fun saveWord(word: String)

    suspend fun deleteWord(word: String)
}