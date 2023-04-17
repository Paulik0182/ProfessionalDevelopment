package com.paulik.professionaldevelopment.data.retrofit

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<DataEntity>>
}