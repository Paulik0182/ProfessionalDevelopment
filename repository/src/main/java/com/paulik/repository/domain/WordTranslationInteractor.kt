package com.paulik.repository.domain

interface WordTranslationInteractor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}