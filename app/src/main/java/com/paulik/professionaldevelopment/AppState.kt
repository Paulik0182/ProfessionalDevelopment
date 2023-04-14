package com.paulik.professionaldevelopment

import com.paulik.professionaldevelopment.domain.entity.DataEntity


sealed class AppState {

    data class Success(val data: List<DataEntity>?) : AppState()

    object Empty : AppState() // todo обозначает что у нас пустой экран

    data class Error(val error: Throwable) : AppState()

    data class Loading(val progress: Int?) : AppState()
}
