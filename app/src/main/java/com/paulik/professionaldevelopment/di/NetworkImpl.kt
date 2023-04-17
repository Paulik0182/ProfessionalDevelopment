package com.paulik.professionaldevelopment.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.paulik.professionaldevelopment.data.BaseInterceptorImpl
import com.paulik.professionaldevelopment.data.retrofit.ApiService
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.domain.source.DataSource
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"

class NetworkImpl : DataSource<List<DataEntity>> {

    override fun getData(word: String): Observable<List<DataEntity>> {
        return getService(BaseInterceptorImpl.interceptor).search(word)
    }

    private fun getService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor).create(ApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient(interceptor))
            .build()
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }
}