package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.domain.StopwatchState
import com.paulik.professionaldevelopment.domain.TimestampProvider

class ElapsedTimeCalculator(
    private val timestampProvider: TimestampProvider,
) {

    fun calculate(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getCurrentMs()
        val timePassedSinceStart =
            if (currentTimestamp > state.startTime) {
                currentTimestamp - state.startTime
            } else {
                0
            }
        return timePassedSinceStart + state.elapsedTime
    }
}