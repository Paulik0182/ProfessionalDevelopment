package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.source.DataSource

class RepositoryImpl(
    private val dataSource: DataSource<List<DataEntity>>
) : Repository<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        return dataSource.getData(word)
    }
}