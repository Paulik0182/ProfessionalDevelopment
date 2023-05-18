package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.domain.StopwatchState
import com.paulik.professionaldevelopment.domain.StopwatchStateCalculator
import com.paulik.professionaldevelopment.domain.TimestampProvider

class StopwatchStateCalculatorImpl(
    private val timestampProvider: TimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) : StopwatchStateCalculator {

    override fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = timestampProvider.getCurrentMs(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    override fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState)
                StopwatchState.Paused(elapsedTime = elapsedTime)
            }

            is StopwatchState.Paused -> oldState
        }
}