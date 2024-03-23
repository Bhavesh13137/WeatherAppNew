package com.bhavesh.weatherappindianic.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.text.SimpleDateFormat
import java.util.Locale

class ConvertDate {
    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
    private val simpleDateFormat2 = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    fun getDateString(time: Long) : String = simpleDateFormat.format(time * 1000L)

    fun getDateString(time: Int) : String = simpleDateFormat.format(time * 1000L)

    fun getDate2String(time: Long) : String = simpleDateFormat2.format(time * 1000L)
    fun getDate2String(time: Int) : String = simpleDateFormat2.format(time * 1000L)

    fun getAddress(latitude : Double,longitude : Double, cotext : Context) : String{
        val geocoder: Geocoder
        val addresses: List<Address>?
        geocoder = Geocoder(cotext, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )


        val address = addresses!![0].getAddressLine(0) //If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        val knownName = addresses[0].featureName


        println(postalCode)
        println(knownName)


        return address
    }

    fun getCity(latitude : Double,longitude : Double, cotext : Context) : String{
        val geocoder: Geocoder
        val addresses: List<Address>?
        geocoder = Geocoder(cotext, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        val address = addresses!![0].getAddressLine(0) //If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        val knownName = addresses[0].featureName


        println(postalCode)
        println(knownName)


        return city
    }

    fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }
}