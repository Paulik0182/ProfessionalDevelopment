package com.paulik.professionaldevelopment.domain.repo

import io.reactivex.Observable

interface Repository<T> {

    fun getData(word: String): Observable<T>
}