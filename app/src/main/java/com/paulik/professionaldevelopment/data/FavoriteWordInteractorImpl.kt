package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.RepositoryLocal
import com.paulik.professionaldevelopment.domain.repo.WordTranslationInteractor

class FavoriteWordInteractorImpl(
    private val repositoryLocal: RepositoryLocal<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val data = repositoryLocal.getData(word)
        return if (data == null) {
            AppState.Error(Exception("Нет данных"))
        } else {
            AppState.Success(data)
        }
    }

}