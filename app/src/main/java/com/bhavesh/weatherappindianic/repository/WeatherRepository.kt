package com.bhavesh.weatherappindianic.repository

import com.bhavesh.weatherappindianic.helper.WeatherApiHelper
import com.bhavesh.weatherappindianic.response.ForecastResponse
import com.bhavesh.weatherappindianic.response.WeatherResponse
import io.reactivex.Observable

class WeatherRepository(private val apiHelper: WeatherApiHelper) {

    fun fetchWeather(lat : String, lon : String, appId : String) : Observable<WeatherResponse> {
        return apiHelper.fetchWeather(lat,lon,appId)
    }

    fun fetchForecast(lat : String, lon : String, appId : String) : Observable<ForecastResponse> {
        return apiHelper.fetchForecast(lat,lon,appId)
    }
}