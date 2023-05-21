package com.paulik.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun String.Companion.getEmptyString(): String = ""

fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
    return this as MutableLiveData
}