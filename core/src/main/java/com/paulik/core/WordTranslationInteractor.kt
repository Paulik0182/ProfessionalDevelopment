package com.paulik.core

interface WordTranslationInteractor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}