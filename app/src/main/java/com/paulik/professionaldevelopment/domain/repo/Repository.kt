package com.paulik.professionaldevelopment.domain.repo

interface Repository<T> {

    suspend fun getData(word: String): T
}