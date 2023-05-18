package com.paulik.professionaldevelopment.ui.stopwatch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulik.professionaldevelopment.domain.StopwatchListOrchestrator
import kotlinx.coroutines.launch

class StopwatchViewModel(
    private val stopwatchListOrchestrator: StopwatchListOrchestrator
) : ViewModel() {

    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String> get() = _currentTime

    private val _secondTime = MutableLiveData<String>()
    val secondTime: LiveData<String> get() = _secondTime


    fun start(id: Int) {
        stopwatchListOrchestrator.start(id)
    }

    fun pause(id: Int) {
        stopwatchListOrchestrator.pause(id)
    }

    fun stop(id: Int) {
        stopwatchListOrchestrator.stop(id)
    }

    fun startTicker() {
        viewModelScope.launch {
            stopwatchListOrchestrator.ticker.collect {
                _currentTime.value = it.getOrNull(0) ?: ""
                _secondTime.value = it.getOrNull(1) ?: ""
                Log.d("@@@", "startTicker: $it")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopwatchListOrchestrator.stop(0)
        stopwatchListOrchestrator.stop(1)
    }
}