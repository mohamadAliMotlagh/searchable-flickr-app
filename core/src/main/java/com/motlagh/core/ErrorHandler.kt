package com.motlagh.core

import androidx.annotation.Keep
import com.google.gson.Gson
import retrofit2.HttpException

/**
 * From network throwable data layer error.
 *
 * @param throwable the throwable
 * @return the data layer error
 */

private const val ERROR_MESSAGE = "خطا در برقراری ارتباط"
fun fromNetworkThrowable(throwable: Throwable): RestErrorResponse {

    throwable.printStackTrace()

    return if (throwable is HttpException) {
        val networkError = throwable.response()
        val errorBodyString = networkError?.errorBody()?.string().toString()
        return try {
            getFlatErrorModel(errorBodyString)
        } catch (e: Exception) {
            getErrorModel(errorBodyString)
        }
    } else {
        RestErrorResponse(-1, ERROR_MESSAGE)
    }

}

@Throws(Exception::class)
fun getFlatErrorModel(json: String?): RestErrorResponse {
    json?.let {
        var errorModel = Gson().fromJson(
            json,
            RestErrorResponse::class.java
        )
        errorModel = RestErrorResponse(errorModel.status, ERROR_MESSAGE)
        return errorModel
    } ?: run {
        return RestErrorResponse(-1, ERROR_MESSAGE)
    }
}


@Keep
data class RestErrorResponse(val status: Int, val message: String)

@Keep
data class RestErrorModel(val errorCode: Int, val message: String?)

@Keep
data class RestErrorResponseModel(val error: RestErrorModel?)


fun getErrorModel(json: String?): RestErrorResponse {
    json?.let {
        try {
            val errorModel = Gson().fromJson(
                json,
                RestErrorResponseModel::class.java
            )
            if (errorModel.error?.message != null) {
                return RestErrorResponse(errorModel.error.errorCode, ERROR_MESSAGE)
            }
            return RestErrorResponse(
                errorModel.error?.errorCode ?: -1, errorModel.error?.message
                    ?: ERROR_MESSAGE
            )
        } catch (e: Exception) {
            return RestErrorResponse(-1, ERROR_MESSAGE)
        }
    } ?: run {
        return RestErrorResponse(-1, ERROR_MESSAGE)
    }
}