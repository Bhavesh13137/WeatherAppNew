package com.bhavesh.weatherappindianic.network

import com.bhavesh.weatherappindianic.service.WeatherApiService
import com.bhavesh.weatherappindianic.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val basicAuth = BasicAuthInterceptor("Bhavesh","BhaveshJi")

        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(logging).connectTimeout(30,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).writeTimeout(25,TimeUnit.SECONDS)
            //this.addInterceptor(basicAuth)
        }.build()

        return Retrofit.Builder()
            .baseUrl(Constant.url) // change this IP for testing by your actual machine IP
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val weatherApiService: WeatherApiService = getRetrofit().create(WeatherApiService::class.java)
}