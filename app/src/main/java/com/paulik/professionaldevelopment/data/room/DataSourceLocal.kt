package com.paulik.professionaldevelopment.data.room

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSource
import io.reactivex.Observable

class DataSourceLocal(
    private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()
) : DataSource<List<DataEntity>> {

    override fun getData(word: String): Observable<List<DataEntity>> = remoteProvider.getData(word)
}