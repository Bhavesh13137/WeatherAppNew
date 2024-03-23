package com.bhavesh.weatherappindianic.response

import android.os.Parcel
import android.os.Parcelable

data class WindX(
    val deg: Int? = null,
    val gust: Double? = null,
    val speed: Double? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(deg)
        parcel.writeValue(gust)
        parcel.writeValue(speed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WindX> {
        override fun createFromParcel(parcel: Parcel): WindX {
            return WindX(parcel)
        }

        override fun newArray(size: Int): Array<WindX?> {
            return arrayOfNulls(size)
        }
    }
}