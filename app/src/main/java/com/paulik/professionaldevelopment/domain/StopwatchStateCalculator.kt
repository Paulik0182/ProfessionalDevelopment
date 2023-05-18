package com.paulik.professionaldevelopment.domain

interface StopwatchStateCalculator {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused
}