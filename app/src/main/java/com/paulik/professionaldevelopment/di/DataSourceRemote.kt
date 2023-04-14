package com.paulik.professionaldevelopment.di

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSource
import io.reactivex.Observable

class DataSourceRemote(
    private val remoteProvider: NetworkImpl = NetworkImpl()
) : DataSource<List<DataEntity>> {

    override fun getData(word: String): Observable<List<DataEntity>> = remoteProvider.getData(word)
}