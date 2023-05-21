package com.paulik.professionaldevelopment.data.retrofit

import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.entity.MeaningsEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Deferred - это интекфейс который позволяет дождатся выполнение некоторого кода.
 */
interface ApiService {

    // https://dictionary.skyeng.ru/api/public/v1/

    @GET("words/search")
    fun searchAsync(
        @Query("search") wordToSearch: String
    ): Deferred<List<DataEntity>>

    // https://dictionary.skyeng.ru/api/public/v1/words/search?search=dog
    @GET("words/search?search=")
    fun wordAsync(
        @Query("word") word: String?
    ): Deferred<MeaningsEntity>
}