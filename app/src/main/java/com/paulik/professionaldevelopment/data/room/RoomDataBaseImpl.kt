package com.paulik.professionaldevelopment.data.room

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSource
import io.reactivex.Observable

class RoomDataBaseImpl : DataSource<List<DataEntity>> {

    override fun getData(word: String): Observable<List<DataEntity>> {
        TODO("not implemented")
    }
}