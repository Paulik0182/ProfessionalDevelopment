package com.paulik.professionaldevelopment.data.retrofit

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Deferred - это интекфейс который позволяет дождатся выполнение некоторого кода.
 */
interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataEntity>>
}