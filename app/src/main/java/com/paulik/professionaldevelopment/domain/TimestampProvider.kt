package com.paulik.professionaldevelopment.domain

interface TimestampProvider {

    fun getCurrentMs(): Long
}