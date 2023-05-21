package com.paulik.professionaldevelopment.data

import com.paulik.core.WordTranslationInteractor
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.repo.HistoryRepo

class HistoryWordTranslationInteractorImpl(
    private val historyRepo: HistoryRepo<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val data = historyRepo.getData(word)
        return if (data == null) {
            AppState.Error(Exception("Нет данных"))
        } else {
            AppState.Success(data)
        }
    }
}
