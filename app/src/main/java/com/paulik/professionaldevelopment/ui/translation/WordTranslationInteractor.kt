package com.paulik.professionaldevelopment.ui.translation

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Interactor
import com.paulik.professionaldevelopment.domain.repo.Repository
import io.reactivex.Observable

class WordTranslationInteractor(
    private val remoteRepository: Repository<List<DataEntity>>,
    private val localRepository: Repository<List<DataEntity>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        // todo Добавили немного логики
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
