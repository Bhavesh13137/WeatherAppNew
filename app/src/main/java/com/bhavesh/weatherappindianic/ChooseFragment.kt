package com.bhavesh.weatherappindianic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.bhavesh.weatherappindianic.fragmentui.CurrentWeatherFragment
import com.bhavesh.weatherappindianic.fragmentui.DaysForecastFragment
import com.google.android.material.tabs.TabLayout

private const val ARG_PARAM1 = "address"
private const val ARG_PARAM2 = "lat"
private const val ARG_PARAM3 = "long"
private const val ARG_PARAM4 = "city"

class ChooseFragment : Fragment() {
    private var param1: String? = null
    private var param2: Double? = null
    private var param3: Double? = null
    private var param4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getDouble(ARG_PARAM2)
            param3 = it.getDouble(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tab_viewpager = view.findViewById<ViewPager>(R.id.tab_viewpager)
        var tab_tablayout = view.findViewById<TabLayout>(R.id.tab_tablayout)
        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)

    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ChooseActivity.ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(getBundleDataForCurrentWeatherFragment(), "Current Weather")
        adapter.addFragment(getBundleDataForDaysForecastFragment(), "5 Days Forecast")
        viewpager.adapter = adapter
    }

    private fun getBundleDataForCurrentWeatherFragment() : CurrentWeatherFragment {
        val bundle = Bundle()
        bundle.putString("address", param1)
        param2?.let { bundle.putDouble("lat", it) }
        param3?.let { bundle.putDouble("long", it) }
        bundle.putString("city", param4)
        val fragObj = CurrentWeatherFragment()
        fragObj.arguments = bundle
        return fragObj
    }

    private fun getBundleDataForDaysForecastFragment() : DaysForecastFragment {
        val bundle = Bundle()
        bundle.putString("address", param1)
        param2?.let { bundle.putDouble("lat", it) }
        param3?.let { bundle.putDouble("long", it) }
        bundle.putString("city", param4)
        val fragObj = DaysForecastFragment()
        fragObj.arguments = bundle
        return fragObj
    }
}