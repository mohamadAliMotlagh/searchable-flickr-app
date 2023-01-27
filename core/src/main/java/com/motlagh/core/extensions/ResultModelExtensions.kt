package com.motlagh.core.extensions

import com.motlagh.core.RestErrorResponse
import com.motlagh.core.ResultModel


inline fun <R, T> ResultModel<T>.executeUseCase(
    ifSuccess: (value: T) -> R,
    ifError: (error: RestErrorResponse) -> R
): R {
    return when (this) {
        is ResultModel.Success<T> -> ifSuccess(value)
        is ResultModel.Error -> ifError(error)
    }
}

fun <T, R> ResultModel<T>.resultModelMap(isSuccess: (ResultModel<T>) -> R): ResultModel<R> {
    return when (this) {
        is ResultModel.Success -> ResultModel.Success(isSuccess.invoke(this))
        is ResultModel.Error -> ResultModel.Error(error)
    }
}

suspend fun <T, R> ResultModel<T>.suspendMapWhenSuccess(isSuccess: suspend (T) -> R): ResultModel<R> {
    return when (this) {
        is ResultModel.Success -> ResultModel.Success(isSuccess.invoke(value))
        is ResultModel.Error -> ResultModel.Error(error)
    }
}

suspend fun <T, R> ResultModel<T>.suspendResultModelMap(isSuccess: suspend (ResultModel<T>) -> R): ResultModel<R> {
    return when (this) {
        is ResultModel.Success -> ResultModel.Success(isSuccess.invoke(this))
        is ResultModel.Error -> ResultModel.Error(error)
    }
}

fun <T, R> ResultModel<T>.map(isSuccess: (T) -> R): ResultModel<R> {
    return when (this) {
        is ResultModel.Success -> {
            ResultModel.Success(isSuccess.invoke(value))
        }
        is ResultModel.Error -> {
            ResultModel.Error(error)
        }
    }
}

suspend fun <T, R> ResultModel<T>.suspendSwitchMap(isSuccess: suspend (T) -> ResultModel<R>): ResultModel<R> {
    return when (this) {
        is ResultModel.Success -> {
            isSuccess.invoke(value)
        }
        is ResultModel.Error -> {
            ResultModel.Error(error)
        }
    }
}

suspend fun <T, R> ResultModel<T>.suspendMap(isSuccess: suspend (T) -> R): ResultModel<R> {
    return when (this) {
        is ResultModel.Success -> {
            ResultModel.Success(isSuccess.invoke(value))
        }
        is ResultModel.Error -> {
            ResultModel.Error(error)
        }
    }
}