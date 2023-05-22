package com.paulik.repository.data

import com.paulik.models.entity.DataEntity
import com.paulik.repository.domain.repo.Repository
import com.paulik.repository.domain.source.DataSource

class RepositoryImpl(
    private val dataSource: DataSource<List<DataEntity>>
) : Repository<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        return dataSource.getData(word)
    }
}