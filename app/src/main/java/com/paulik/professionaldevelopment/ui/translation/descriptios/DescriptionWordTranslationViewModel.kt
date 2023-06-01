package com.paulik.professionaldevelopment.ui.translation.descriptios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulik.models.entity.MeaningsEntity
import com.paulik.repository.data.retrofit.ApiService

class DescriptionWordTranslationViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _wordDetails = MutableLiveData<WordDetailsResult>()
    val wordDetails: LiveData<WordDetailsResult> = _wordDetails

    suspend fun getWordDetails(word: String?) {
        _wordDetails.value = WordDetailsResult.Loading

        try {
            val response = apiService.wordAsync(word).await()
            if (true) {
                _wordDetails.value = WordDetailsResult.Success(response)
            } else {
                _wordDetails.value = WordDetailsResult.Error("No results found")
            }
        } catch (e: Exception) {
            _wordDetails.value = WordDetailsResult.Error("An error occurred")
            }
        }
    }

sealed class WordDetailsResult {
    object Loading : WordDetailsResult()
    data class Success(val wordDetails: MeaningsEntity) : WordDetailsResult()
    data class Error(val message: String) : WordDetailsResult()
}