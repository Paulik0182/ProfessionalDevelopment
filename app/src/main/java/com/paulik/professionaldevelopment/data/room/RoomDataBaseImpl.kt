package com.paulik.professionaldevelopment.data.room

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSource

class RoomDataBaseImpl : DataSource<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        TODO("not implemented")
    }
}