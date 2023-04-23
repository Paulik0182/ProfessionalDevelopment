package com.paulik.professionaldevelopment.domain.source

interface DataSource<T> {

    suspend fun getData(word: String): T
}