package com.paulik.repository.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.paulik.models.entity.DataEntity
import com.paulik.models.entity.MeaningsEntity
import com.paulik.repository.data.BaseInterceptorImpl
import com.paulik.repository.domain.source.DataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"

class RetrofitImpl : DataSource<List<DataEntity>> {

    override suspend fun getData(word: String): List<DataEntity> {
        /** await() - функция позволяет получать результат в данном месте.
         * То-есть, карутины будут остановленны именно здесь пока мы не получим какойто результат.*/
        return getService(BaseInterceptorImpl.interceptor).searchAsync(word).await()
    }

    suspend fun getDetailsWord(word: String): MeaningsEntity {
        return getService(BaseInterceptorImpl.interceptor).wordAsync(word).await()
    }

    private fun getService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor).create(ApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
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