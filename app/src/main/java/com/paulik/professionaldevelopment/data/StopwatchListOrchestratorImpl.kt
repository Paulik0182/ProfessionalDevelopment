package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.domain.StopwatchListOrchestrator
import com.paulik.professionaldevelopment.domain.StopwatchStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class StopwatchListOrchestratorImpl : StopwatchListOrchestrator, KoinComponent {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val stopwatches: HashMap<Int, StopwatchStateHolder> = HashMap()

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(listOf<String>())
    override val ticker: StateFlow<List<String>> = mutableTicker

    private fun startJob() {
        scope.launch {
            while (isActive) {
                mutableTicker.value = stopwatches.values.map {
                    it.getStringTimeRepresentation()
                }
                delay(20)
            }
        }
    }


    private fun getStopwatch(id: Int): StopwatchStateHolder {
        if (!stopwatches.containsKey(id)) {
            stopwatches[id] =
                get<StopwatchStateHolder>()
        }

        return stopwatches[id]!!
    }

    override fun start(id: Int) {
        if (job == null) startJob()
        getStopwatch(id).start()
    }

    override fun pause(id: Int) {
        getStopwatch(id).pause()
        stopJob()
    }

    override fun stop(id: Int) {
        getStopwatch(id).stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {

        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = emptyList()
    }
}