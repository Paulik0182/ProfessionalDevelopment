package com.paulik.professionaldevelopment.domain.repo

interface WordTranslationInteractor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}