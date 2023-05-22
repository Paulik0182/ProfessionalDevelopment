package com.paulik.repository.domain.repo

interface Repository<T> {

    suspend fun getData(word: String): T
}