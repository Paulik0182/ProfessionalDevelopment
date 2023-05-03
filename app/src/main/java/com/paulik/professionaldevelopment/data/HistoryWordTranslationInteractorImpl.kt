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
        val repository = if (fromRemoteSource) repositoryRemote else repositoryLocal
        val data = repository.getData(word)
        return if (data.isNullOrEmpty()) {
            AppState.Error(Exception("Нет данных"))
        } else {
            AppState.Success(data)
        }
    }
}
