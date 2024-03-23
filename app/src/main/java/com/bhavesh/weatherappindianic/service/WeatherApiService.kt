package com.bhavesh.weatherappindianic.service

import com.bhavesh.weatherappindianic.response.ForecastResponse
import com.bhavesh.weatherappindianic.response.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    fun fetchWeather(@Query("lat") lat : String, @Query("lon") lon : String, @Query("appid") appid : String): Observable<WeatherResponse>

    @GET("forecast")
    fun fetchForecast(@Query("lat") lat : String, @Query("lon") lon : String, @Query("appid") appid : String): Observable<ForecastResponse>
}