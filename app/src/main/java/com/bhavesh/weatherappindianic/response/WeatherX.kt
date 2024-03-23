package com.bhavesh.weatherappindianic.response

import android.os.Parcel
import android.os.Parcelable

data class WeatherX(
    val description: String? = null,
    val icon: String? = null,
    val id: Int? = null,
    val main: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(icon)
        parcel.writeValue(id)
        parcel.writeString(main)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherX> {
        override fun createFromParcel(parcel: Parcel): WeatherX {
            return WeatherX(parcel)
        }

        override fun newArray(size: Int): Array<WeatherX?> {
            return arrayOfNulls(size)
        }
    }
}