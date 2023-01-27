package com.motlagh.core.calladapter


import com.motlagh.core.ResultModel
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResultModelAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        // suspend functions wrap the response type in `Call`

        if (Call::class.java != getRawType(returnType)) {

            return null

        }
        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "error"
        }
        // get the response type inside the `Call` type

        val responseType = getParameterUpperBound(0, returnType)

        // if the response type is not ApiResponse then we can't handle this type, so we return null

        if (getRawType(responseType) != ResultModel::class.java) {
            return null
        }
        // the response type is ApiResponse and should be parameterized
        check(responseType is ParameterizedType) { "error" }
        val successBodyType = getParameterUpperBound(0, responseType)

        return NetworkResultModelAdapter<Any>(successBodyType)

    }

    companion object {
        @JvmStatic
        fun create() = NetworkResultModelAdapterFactory()
    }
}