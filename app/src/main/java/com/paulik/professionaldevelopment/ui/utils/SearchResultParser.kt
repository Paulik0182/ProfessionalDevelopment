package com.paulik.professionaldevelopment.ui.utils

import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.entity.MeaningsEntity

fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<DataEntity>()
    when (state) {
        is AppState.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
        else -> {}
    }

    return AppState.Success(newSearchResults)
}

private fun parseResult(dataEntity: DataEntity, newDataModels: ArrayList<DataEntity>) {
    if (!dataEntity.text.isNullOrBlank() && !dataEntity.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<MeaningsEntity>()
        for (meaning in dataEntity.meanings) {
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                newMeanings.add(MeaningsEntity(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataEntity(dataEntity.text, newMeanings))
        }
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