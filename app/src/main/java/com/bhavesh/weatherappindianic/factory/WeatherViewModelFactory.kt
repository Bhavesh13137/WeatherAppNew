package com.bhavesh.weatherappindianic.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhavesh.weatherappindianic.repository.WeatherRepository
import com.bhavesh.weatherappindianic.view_model.WeatherViewModel


class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}