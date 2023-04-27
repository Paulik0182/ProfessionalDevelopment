package com.paulik.professionaldevelopment.domain.source

import com.paulik.professionaldevelopment.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}