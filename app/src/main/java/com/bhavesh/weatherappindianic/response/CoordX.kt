package com.bhavesh.weatherappindianic.response

import android.os.Parcel
import android.os.Parcelable

data class CoordX(
    val lat: Double? = null,
    val lon: Double? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(lat)
        parcel.writeValue(lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoordX> {
        override fun createFromParcel(parcel: Parcel): CoordX {
            return CoordX(parcel)
        }

        override fun newArray(size: Int): Array<CoordX?> {
            return arrayOfNulls(size)
        }
    }
}