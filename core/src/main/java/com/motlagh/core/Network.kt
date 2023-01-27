package com.motlagh.core

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
            .baseUrl("")// its could be in BuildConfig.
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
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


    private fun OkHttpClient.Builder.logInterceptor(): OkHttpClient.Builder {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return this.addInterceptor(interceptor)
    }
}
