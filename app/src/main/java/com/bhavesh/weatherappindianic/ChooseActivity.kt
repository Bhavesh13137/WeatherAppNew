package com.bhavesh.weatherappindianic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bhavesh.weatherappindianic.factory.WeatherViewModelFactory
import com.bhavesh.weatherappindianic.fragmentui.CurrentWeatherFragment
import com.bhavesh.weatherappindianic.fragmentui.DaysForecastFragment
import com.bhavesh.weatherappindianic.helper.WeatherApiHelper
import com.bhavesh.weatherappindianic.network.RetrofitBuilder
import com.bhavesh.weatherappindianic.repository.WeatherRepository
import com.bhavesh.weatherappindianic.view_model.WeatherViewModel
import com.google.android.material.tabs.TabLayout

class ChooseActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        var tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        var tab_tablayout = findViewById<TabLayout>(R.id.tab_tablayout)
        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)
        val service = RetrofitBuilder.weatherApiService
        val helper = WeatherApiHelper(service)
        val repository = WeatherRepository(helper)
        viewModel = ViewModelProvider(this, WeatherViewModelFactory(repository))[WeatherViewModel::class.java]

    }

    private fun getBundleDataForCurrentWeatherFragment() : CurrentWeatherFragment{
        val bundle = Bundle()
        bundle.putString("address", intent.getStringExtra("address"))
        bundle.putDouble("lat", intent.getDoubleExtra("lat",0.0))
        bundle.putDouble("long", intent.getDoubleExtra("long",0.0))
        bundle.putString("city", intent.getStringExtra("city"))
        val fragObj = CurrentWeatherFragment()
        fragObj.arguments = bundle
        return fragObj
    }

    private fun getBundleDataForDaysForecastFragment() : DaysForecastFragment{
        val bundle = Bundle()
        bundle.putString("address", intent.getStringExtra("address"))
        bundle.putDouble("lat", intent.getDoubleExtra("lat",0.0))
        bundle.putDouble("long", intent.getDoubleExtra("long",0.0))
        bundle.putString("city", intent.getStringExtra("city"))
        val fragObj = DaysForecastFragment()
        fragObj.arguments = bundle
        return fragObj
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ViewPagerAdapter(supportFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab

        adapter.addFragment(getBundleDataForCurrentWeatherFragment(), "Current Weather")
        adapter.addFragment(getBundleDataForDaysForecastFragment(), "5 Days Forecast")

        // setting adapter to view pager.
        viewpager.adapter = adapter
    }

    class ViewPagerAdapter : FragmentPagerAdapter {

        // objects of arraylist. One is of Fragment type and
        // another one is of String type.*/
        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        // this is a secondary constructor of ViewPagerAdapter class.
        public constructor(supportFragmentManager: FragmentManager)
                : super(supportFragmentManager)

        // returns which item is selected from arraylist of fragments.
        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        // returns which item is selected from arraylist of titles.
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1[position]
        }

        // returns the number of items present in arraylist.
        override fun getCount(): Int {
            return fragmentList1.size
        }

        // this function adds the fragment and title in 2 separate  arraylist.
        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }
}