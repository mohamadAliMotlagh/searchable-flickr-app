package com.motlagh.core

sealed class ResultModel<out V> {

    data class Success<V>(val value: V) : ResultModel<V>()

    data class Error(val error: RestErrorResponse) : ResultModel<Nothing>()
}