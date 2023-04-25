package com.paulik.professionaldevelopment.data.room

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.RepositoryLocal
import com.paulik.professionaldevelopment.domain.source.DataSourceLocal

class RepositoryLocalImpl(
    private val dataSource: DataSourceLocal<List<DataEntity>>
) : RepositoryLocal<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}