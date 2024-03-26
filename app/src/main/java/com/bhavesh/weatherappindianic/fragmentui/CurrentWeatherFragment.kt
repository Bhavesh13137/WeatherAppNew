package com.bhavesh.weatherappindianic.fragmentui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bhavesh.weatherappindianic.R
import com.bhavesh.weatherappindianic.factory.WeatherViewModelFactory
import com.bhavesh.weatherappindianic.helper.WeatherApiHelper
import com.bhavesh.weatherappindianic.network.RetrofitBuilder
import com.bhavesh.weatherappindianic.repository.WeatherRepository
import com.bhavesh.weatherappindianic.utils.Constant
import com.bhavesh.weatherappindianic.utils.ConvertDate
import com.bhavesh.weatherappindianic.view_model.WeatherViewModel
import com.bumptech.glide.Glide
import java.text.DecimalFormat

private const val ARG_PARAM1 = "address"
private const val ARG_PARAM2 = "lat"
private const val ARG_PARAM3 = "long"
private const val ARG_PARAM4 = "city"

class CurrentWeatherFragment : Fragment() {

    private var param1: String? = null
    private var param2: Double? = null
    private var param3: Double? = null
    private var param4: Double? = null
    private lateinit var viewModel: WeatherViewModel
    private lateinit var progressBar : ProgressBar
    private lateinit var weatherCity : TextView
    private lateinit var todayTitle : TextView
    private lateinit var temp : TextView
    private lateinit var desc : TextView
    private lateinit var minTemp : TextView
    private lateinit var maxTemp : TextView
    private lateinit var humidity : TextView
    private lateinit var icon : ImageView
    private lateinit var currentWeatherContainer : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getDouble(ARG_PARAM2,0.0)
            param3 = it.getDouble(ARG_PARAM3,0.0)
            param4 = it.getDouble(ARG_PARAM4,0.0)
        }
    }

    private fun showNewProgress() {
        progressBar.isIndeterminate = true
        progressBar.visibility = View.VISIBLE
        weatherCity.visibility = View.GONE
        currentWeatherContainer.visibility = View.GONE
    }

    private fun hideNewProgress() {
        progressBar.isIndeterminate = false
        progressBar.visibility = View.GONE
        weatherCity.visibility = View.VISIBLE
        currentWeatherContainer.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        currentWeatherContainer = view.findViewById<ConstraintLayout>(R.id.currentWeatherContainer)
        weatherCity = view.findViewById<TextView>(R.id.weatherCity)
        todayTitle = view.findViewById<TextView>(R.id.todayTitle)
        temp = view.findViewById<TextView>(R.id.temp)
        desc = view.findViewById<TextView>(R.id.desc)
        minTemp = view.findViewById<TextView>(R.id.minTemp)
        maxTemp = view.findViewById<TextView>(R.id.maxTemp)
        humidity = view.findViewById<TextView>(R.id.humidity)
        icon = view.findViewById<ImageView>(R.id.icon)

        val service = RetrofitBuilder.weatherApiService
        val helper = WeatherApiHelper(service)
        val repository = WeatherRepository(helper)
        viewModel = ViewModelProvider(requireActivity(), WeatherViewModelFactory(repository))[WeatherViewModel::class.java]
        init()
    }

    private fun init(){
        viewModel.fetchWeather(param2.toString(),param3.toString(), Constant.apikey)
        observer()
    }

    private fun observer(){
        showNewProgress()
        viewModel.listWeather.observe(viewLifecycleOwner){
            hideNewProgress()
            weatherCity.text = it.name
            todayTitle.text = it.dt?.let { a -> ConvertDate.getDateString(a) }
            minTemp.text = it.main?.temp_min.toString()
            maxTemp.text = it.main?.temp_max.toString()
            humidity.text = it.main?.humidity.toString()
            desc.text = it.weather?.get(0)?.description
            temp.text = it.main?.temp!!.toString().plus("°C")
            val imageUrl = Constant.imageUrl.plus(it.weather?.get(0)?.icon.toString()).plus(".png")
            Glide.with(icon.context).load(imageUrl).into(icon)
        }
    }

}