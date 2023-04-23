package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.repo.WordTranslationInteractor

class WordTranslationInteractorImpl(
    private val remoteRepository: Repository<List<DataEntity>>,
    private val localRepository: Repository<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        if (word.isBlank()) {
            return AppState.Empty
        }

        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}