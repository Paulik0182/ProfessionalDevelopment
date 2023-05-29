package com.paulik.repository.data

import com.paulik.models.AppState
import com.paulik.models.entity.DataEntity
import com.paulik.repository.domain.WordTranslationInteractor
import com.paulik.repository.domain.repo.HistoryRepo
import com.paulik.repository.domain.repo.Repository

class WordTranslationInteractorImpl(
    private val remoteRepository: Repository<List<DataEntity>>,
    private val localRepository: HistoryRepo<List<DataEntity>>
) : WordTranslationInteractor<AppState> {

    private lateinit var appState: AppState

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        if (word.isBlank()) {
            return AppState.Empty
        }

        try {
            if (fromRemoteSource) {
                remoteRepository.getData(word).also { list ->
                    appState = AppState.Success(list.map {
                        DataEntity(
                            it.text,
                            it.meanings
                        )
                    })
                    localRepository.saveToDB(appState)
                }
            } else {
                localRepository.getData(word)
            }

            return if (appState != null || appState != emptyList<DataEntity>()) {
                appState
            } else {
                AppState.Error(Exception("Нет Данных"))
            }
        } catch (e: Exception) {
            return AppState.Error(e)
        }
    }
}