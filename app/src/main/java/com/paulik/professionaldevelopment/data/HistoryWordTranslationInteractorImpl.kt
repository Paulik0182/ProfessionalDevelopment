package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.Repository
import com.paulik.professionaldevelopment.domain.repo.RepositoryLocal
import com.paulik.professionaldevelopment.domain.repo.WordTranslationInteractor

class HistoryWordTranslationInteractorImpl(
    private val repositoryRemote: Repository<List<DataEntity>>,
    private val repositoryLocal: RepositoryLocal<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        if (word.isBlank()) {
            return AppState.Empty
        }

        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
