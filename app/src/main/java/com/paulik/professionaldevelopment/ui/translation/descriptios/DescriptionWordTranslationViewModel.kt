package com.paulik.professionaldevelopment.ui.translation.descriptios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulik.professionaldevelopment.data.retrofit.ApiService
import com.paulik.professionaldevelopment.domain.entity.MeaningsEntity

class DescriptionWordTranslationViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _wordDetails = MutableLiveData<WordDetailsResult>()
    val wordDetails: LiveData<WordDetailsResult> = _wordDetails

    suspend fun getWordDetails(word: String?) {
        _wordDetails.value = WordDetailsResult.Loading
//        viewModelScope.launch {
//            val response = apiService.wordAsync(word).await()
//            _wordDetails.value = WordDetailsResult.Success(response)

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