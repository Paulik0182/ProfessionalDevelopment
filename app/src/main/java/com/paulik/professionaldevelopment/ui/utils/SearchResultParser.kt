package com.paulik.professionaldevelopment.ui.utils

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.room.HistoryEntity
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.entity.MeaningsEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataEntity> {
    return list.map {
        DataEntity(it.word, null)
    }

//    val searchResult = ArrayList<DataEntity>()
//    if (!list.isNullOrEmpty()) {
//        for (entity in list) {
//            searchResult.add(DataEntity(entity.word, null))
//        }
//    }
//    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text!!, null)
            }
        }
        else -> null
    }
}

// TODO Не понятно для чего.
fun convertMeaningsToString(meanings: List<MeaningsEntity>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma
}

fun parseOnlineSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, true))
}

fun parseLocalSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, false))
}

private fun mapResult(
    appState: AppState,
    isOnline: Boolean
): List<DataEntity> {
    val newSearchResults = arrayListOf<DataEntity>()
    when (appState) {
        is AppState.Success -> {
            getSuccessResultData(appState, isOnline, newSearchResults)
        }
        else -> {}
    }
    return newSearchResults
}

private fun getSuccessResultData(
    appState: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<DataEntity>
) {
    val dataModels: List<DataEntity> = appState.data as List<DataEntity>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(DataEntity(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(dataModel: DataEntity, newDataModels: ArrayList<DataEntity>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<MeaningsEntity>()
        for (meaning in dataModel.meanings) {
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                newMeanings.add(MeaningsEntity(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataEntity(dataModel.text, newMeanings))
        }
    }
}