package com.paulik.professionaldevelopment.domain

interface StopwatchStateHolder {

    fun start()

    fun pause()

    fun stop()

    fun getStringTimeRepresentation(): String
}