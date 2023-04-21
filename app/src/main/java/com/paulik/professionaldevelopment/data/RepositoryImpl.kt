package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.source.DataSource
import io.reactivex.Observable

class RepositoryImpl(
    private val dataSource: DataSource<List<DataEntity>>
) : Repository<List<DataEntity>> {

    override fun getData(word: String): Observable<List<DataEntity>> {
        return dataSource.getData(word)
    }
}