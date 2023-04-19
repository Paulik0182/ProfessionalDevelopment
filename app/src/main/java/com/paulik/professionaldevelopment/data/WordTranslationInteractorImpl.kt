package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.di.modules.NAME_LOCAL
import com.paulik.professionaldevelopment.di.modules.NAME_REMOTE
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.repo.WordTranslationInteractor
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class WordTranslationInteractorImpl @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<DataEntity>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        if (word.isBlank()) {
            return Observable.just(AppState.Empty)
        }
        return if (fromRemoteSource) {
            remoteRepository
        } else {
            localRepository
        }.getData(word).map { AppState.Success(it) }
    }
}