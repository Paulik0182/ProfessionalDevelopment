package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.repo.RepositoryLocal
import com.paulik.professionaldevelopment.domain.repo.WordTranslationInteractor

class WordTranslationInteractorImpl(
    private val remoteRepository: Repository<List<DataEntity>>,
    private val localRepository: RepositoryLocal<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        if (word.isBlank()) {
            return AppState.Empty
        }

        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        return appState
    }
}