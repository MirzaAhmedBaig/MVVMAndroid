package com.mab.mvvmandroid.data.network

import com.google.gson.GsonBuilder
import com.mab.mvvmandroid.data.network.Constants.Companion.BASE_URL
import com.mab.mvvmandroid.data.network.Constants.Companion.DEBUG
import com.mab.mvvmandroid.data.network.Constants.Companion.REQUEST_TIMEOUT_DURATION
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): ApiManager {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(getFactory()))
            .client(createRequestInterceptorClient(networkConnectionInterceptor))
            .build()
            .create(ApiManager::class.java)
    }

    private fun getFactory() = GsonBuilder()
        .enableComplexMapKeySerialization()
        .setPrettyPrinting()
        .create()

    private fun createRequestInterceptorClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient {

        return if (DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .connectTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .build()
        }
    }
}