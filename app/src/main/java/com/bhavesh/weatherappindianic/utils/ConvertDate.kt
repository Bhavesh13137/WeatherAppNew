package com.bhavesh.weatherappindianic.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Locale

class ConvertDate {

    companion object {

        private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
        private val simpleDateFormat2 = SimpleDateFormat("dd MMMM yyyy, HH:m", Locale.ENGLISH)
        fun getDateString(time: Long): String = simpleDateFormat.format(time * 1000L)

        fun getDateString(time: Int): String = simpleDateFormat.format(time * 1000L)

        fun getDate2String(time: Long): String = simpleDateFormat2.format(time * 1000L)
        fun getDate2String(time: Int): String = simpleDateFormat2.format(time * 1000L)

        fun getAddress(latitude: Double, longitude: Double, context: Context): String? {
            return getListOfAddress(latitude,longitude,context)?.get(0)?.getAddressLine(0)
        }

        fun getCity(latitude: Double, longitude: Double, context: Context): String? {
            return getListOfAddress(latitude,longitude,context)?.get(0)?.locality
        }

        private fun getListOfAddress(latitude: Double, longitude: Double, context: Context): List<Address>? {
            val geocoder = Geocoder(context, Locale.getDefault())
            return geocoder.getFromLocation(latitude, longitude, 1)
        }

        fun celsiusToFahrenheit(celsius: Double): Double {
            return (celsius * 9 / 5) + 32
        }

        fun fahrenheitToCelsius(fahrenheit: Double): Double {
            return (fahrenheit - 32) * 5 / 9
        }
    }
}