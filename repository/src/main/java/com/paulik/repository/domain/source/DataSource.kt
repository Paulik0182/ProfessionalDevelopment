package com.paulik.repository.domain.source

interface DataSource<T> {

    suspend fun getData(word: String): T
}