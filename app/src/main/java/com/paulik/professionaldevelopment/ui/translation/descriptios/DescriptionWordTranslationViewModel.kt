package com.paulik.professionaldevelopment.ui.translation.descriptios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulik.professionaldevelopment.data.retrofit.ApiService
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import kotlinx.coroutines.launch

class DescriptionWordTranslationViewModel(
    private val apiService: ApiService
) : ViewModel() {

    //    private val wordDetails = MutableLiveData<List<DataEntity>>()
    private val _wordDetails = MutableLiveData<WordDetailsResult>()
    val wordDetails: LiveData<WordDetailsResult> = _wordDetails

    fun getWordDetails(word: String) {
        _wordDetails.value = WordDetailsResult.Loading
        viewModelScope.launch {
            try {
                val response = apiService.searchAsync(word).await()
                if (response.isNotEmpty()) {
                    _wordDetails.value = WordDetailsResult.Success(response)
                } else {
                    _wordDetails.value = WordDetailsResult.Error("No results found")
                }
            } catch (e: Exception) {
                _wordDetails.value = WordDetailsResult.Error("An error occurred")
            }
        }
    }

//    fun loadWordDetails(word: String){
//        viewModelScope.launch(Dispatchers.IO){
//            val result: List<DataEntity> =
//                try {
//                    apiService.searchAsync(word).await()
//                } catch (e: Exception){
//                    emptyList()
//                }
//            withContext(Dispatchers.Main){
//                wordDetails.value = result
//            }
//        }
//    }
}

sealed class WordDetailsResult {
    object Loading : WordDetailsResult()
    data class Success(val wordDetails: List<DataEntity>) : WordDetailsResult()
    data class Error(val message: String) : WordDetailsResult()
}