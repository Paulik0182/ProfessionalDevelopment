package com.paulik.professionaldevelopment.domain.repo

import com.paulik.professionaldevelopment.AppState


interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
