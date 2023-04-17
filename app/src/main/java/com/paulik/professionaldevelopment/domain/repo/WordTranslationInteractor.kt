package com.paulik.professionaldevelopment.domain.repo

import io.reactivex.Observable

interface WordTranslationInteractor<T> {

    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}