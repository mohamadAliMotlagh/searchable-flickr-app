package com.motlagh.core

import android.os.Build
import com.motlagh.core.calladapter.NetworkResultModelAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


class NetworkManager() {

    fun <T> create(serviceClass: Class<T>): T = getClient().create(serviceClass)

    private fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://flickr.com/services/")// its could be in BuildConfig.
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultModelAdapterFactory.create())
            .client(OkHttpClient.Builder()
                .headerInterceptor()
                .apply {
//                    if (BuildConfig.DEBUG) {
                    logInterceptor()
                    //  }
                }
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build())
            .build()
    }


    private fun OkHttpClient.Builder.headerInterceptor(): OkHttpClient.Builder {
        return this.addInterceptor { chain ->
            val res: okhttp3.Response
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder
                .method(original.method, original.body)
                .header("Content-Type", "application/json")
                .build()
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    private fun OkHttpClient.Builder.logInterceptor(): OkHttpClient.Builder {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return this.addInterceptor(interceptor)
    }
}
