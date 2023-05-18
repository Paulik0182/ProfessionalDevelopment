package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.domain.StopwatchState
import com.paulik.professionaldevelopment.domain.StopwatchStateCalculator
import com.paulik.professionaldevelopment.domain.StopwatchStateHolder
import com.paulik.professionaldevelopment.ui.utils.TimestampMillisecondsFormatter

class StopwatchStateHolderImpl(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) : StopwatchStateHolder {

    var currentState: StopwatchState = StopwatchState.Paused(0)
        private set

    override fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    override fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    override fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    override fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }
}