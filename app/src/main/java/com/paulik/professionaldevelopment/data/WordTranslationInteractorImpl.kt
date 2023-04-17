package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.repo.WordTranslationInteractor
import io.reactivex.Observable

class WordTranslationInteractorImpl(
    private val remoteRepository: Repository<List<DataEntity>>,
    private val localRepository: Repository<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        if (word.isBlank()) {
            return Observable.just(AppState.Empty)
        }
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
