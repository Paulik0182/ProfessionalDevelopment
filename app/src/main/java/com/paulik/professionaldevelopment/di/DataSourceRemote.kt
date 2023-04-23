package com.paulik.professionaldevelopment.di

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSource

class DataSourceRemote(
    private val remoteProvider: NetworkImpl = NetworkImpl()
) : DataSource<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> = remoteProvider.getData(word)
}