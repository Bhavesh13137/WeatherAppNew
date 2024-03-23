package com.bhavesh.weatherappindianic.fragmentui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhavesh.weatherappindianic.R
import com.bhavesh.weatherappindianic.adapter.ForecastAdapter
import com.bhavesh.weatherappindianic.factory.WeatherViewModelFactory
import com.bhavesh.weatherappindianic.helper.WeatherApiHelper
import com.bhavesh.weatherappindianic.network.RetrofitBuilder
import com.bhavesh.weatherappindianic.repository.WeatherRepository
import com.bhavesh.weatherappindianic.utils.Constant
import com.bhavesh.weatherappindianic.view_model.WeatherViewModel
private const val ARG_PARAM1 = "address"
private const val ARG_PARAM2 = "lat"
private const val ARG_PARAM3 = "long"
private const val ARG_PARAM4 = "city"


class DaysForecastFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var progressBar : ProgressBar
    private val adapter = ForecastAdapter()
    private var param1: String? = null
    private var param2: Double? = null
    private var param3: Double? = null
    private var param4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getDouble(ARG_PARAM2,0.0)
            param3 = it.getDouble(ARG_PARAM3,0.0)
            param1 = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_days_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val recView = view.findViewById<RecyclerView>(R.id.recViewCurrentWeather)
        recView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recView.adapter = adapter

        val service = RetrofitBuilder.weatherApiService
        val helper = WeatherApiHelper(service)
        val repository = WeatherRepository(helper)
        viewModel = ViewModelProvider(requireActivity(), WeatherViewModelFactory(repository))[WeatherViewModel::class.java]
        init()
    }

    private fun init(){
        viewModel.fetchForecast(param2.toString(),param3.toString(),Constant.apikey)
        observer()
    }

    private fun observer(){
        showNewProgress()
        viewModel.listForecast.observe(viewLifecycleOwner){
            hideNewProgress()
            adapter.setList(it.list!!)
        }
    }

    private fun showNewProgress() {
        progressBar.isIndeterminate = true
        progressBar.visibility = View.VISIBLE
    }

    private fun hideNewProgress() {
        progressBar.isIndeterminate = false
        progressBar.visibility = View.GONE
    }

}