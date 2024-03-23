package com.bhavesh.weatherappindianic.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bhavesh.weatherappindianic.repository.WeatherRepository
import com.bhavesh.weatherappindianic.response.ForecastResponse
import com.bhavesh.weatherappindianic.response.WeatherResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private var _listWeather : MutableLiveData<WeatherResponse> = MutableLiveData<WeatherResponse>()
    val listWeather : LiveData<WeatherResponse>
    get() = _listWeather

    private var _listForecast : MutableLiveData<ForecastResponse> = MutableLiveData<ForecastResponse>()
    val listForecast : LiveData<ForecastResponse>
    get() = _listForecast

    private var _one : MutableLiveData<Int> = MutableLiveData<Int>()
        val one : LiveData<Int>
    get() = _one

    private var _two : MutableLiveData<Int> = MutableLiveData<Int>()
        val two : LiveData<Int>
    get() = _two


    fun fetchWeather(param1: String, param2: String, param3: String) {
            repository.fetchWeather(param1, param2, param3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<WeatherResponse> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: WeatherResponse) {
                        _listWeather.postValue(t)
                    }
                })
    }

    fun fetchForecast(param1: String, param2: String, param3: String) {
        repository.fetchForecast(param1, param2, param3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ForecastResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: ForecastResponse) {
                    _listForecast.postValue(t)
                }
            })
    }

}