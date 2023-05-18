package com.paulik.professionaldevelopment.data

import com.paulik.professionaldevelopment.domain.TimestampProvider

class TimestampProviderImpl : TimestampProvider {

    override fun getCurrentMs(): Long {
        return System.currentTimeMillis()
    }
}