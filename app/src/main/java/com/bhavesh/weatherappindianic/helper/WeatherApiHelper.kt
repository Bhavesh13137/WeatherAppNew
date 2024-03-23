package com.bhavesh.weatherappindianic.helper

import com.bhavesh.weatherappindianic.service.WeatherApiService

class WeatherApiHelper(private val apiService: WeatherApiService) {
    fun fetchWeather( lat : String, lon : String, appId : String) =  apiService.fetchWeather(lat, lon, appId)

    fun fetchForecast( lat : String, lon : String, appId : String) =  apiService.fetchForecast(lat, lon, appId)
}