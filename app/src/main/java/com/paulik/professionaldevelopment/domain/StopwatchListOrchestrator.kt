package com.paulik.professionaldevelopment.domain

import kotlinx.coroutines.flow.StateFlow

interface StopwatchListOrchestrator {

    val ticker: StateFlow<List<String>>

    fun start(id: Int)

    fun pause(id: Int)

    fun stop(id: Int)
}