package com.motlagh.core.calladapter


import com.motlagh.core.ResultModel
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


class NetworkResultModelAdapter<S>(
    private val successType: Type
) : CallAdapter<S, Call<ResultModel<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<ResultModel<S>> = NetworkResponseCall(call)
}