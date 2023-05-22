package com.paulik.repository.data

import com.paulik.models.AppState
import com.paulik.models.entity.DataEntity
import com.paulik.repository.domain.WordTranslationInteractor
import com.paulik.repository.domain.repo.HistoryRepo

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
