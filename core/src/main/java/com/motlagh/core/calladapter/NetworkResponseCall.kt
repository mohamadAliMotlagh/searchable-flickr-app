package com.motlagh.core.calladapter


import com.motlagh.core.ResultModel
import com.motlagh.core.fromNetworkThrowable
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class NetworkResponseCall<S>(

    private val delegate: Call<S>,
) : Call<ResultModel<S>> {

    override fun enqueue(callback: Callback<ResultModel<S>>) {
        return delegate.enqueue(object : Callback<S> {

            override fun onResponse(call: Call<S>, response: Response<S>) {

                val body = response.body()
                if (response.isSuccessful) {
                    try {
                        body?.let {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(ResultModel.Success(it))
                            )
                        } ?: handleError(callback, HttpException(response))
                    } catch (throwable: Throwable) {
                        handleError(callback, throwable)
                    }
                } else {
                    handleError(callback, HttpException(response))
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(ResultModel.Error(fromNetworkThrowable(t)))
                )
            }
        })
    }

    private fun handleError(callback: Callback<ResultModel<S>>, throwable: Throwable) {
        callback.onResponse(
            this@NetworkResponseCall,
            Response.success(ResultModel.Error(fromNetworkThrowable(throwable)))
        )
    }

    override fun isExecuted() = delegate.isExecuted
    override fun clone() = NetworkResponseCall(delegate.clone())
    override fun isCanceled() = delegate.isCanceled
    override fun cancel() = delegate.cancel()
    override fun execute(): Response<ResultModel<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}