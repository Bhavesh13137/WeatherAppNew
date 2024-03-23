package com.bhavesh.weatherappindianic.response

import android.os.Parcel
import android.os.Parcelable

data class ListData(
    val clouds: CloudsX? = null,
    val dt: Int? = null,
    val dt_txt: String? = null,
    val main: MainX? = null,
    val pop: Double? = null,
    val rain: Rain? = null,
    val sys: SysX? = null,
    val visibility: Int? = null,
    val weather: List<WeatherX>? = null,
    val wind: WindX? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("clouds"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readParcelable(MainX::class.java.classLoader),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        TODO("rain"),
        parcel.readParcelable(SysX::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(WeatherX),
        parcel.readParcelable(WindX::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(dt)
        parcel.writeString(dt_txt)
        parcel.writeParcelable(main, flags)
        parcel.writeValue(pop)
        parcel.writeParcelable(sys, flags)
        parcel.writeValue(visibility)
        parcel.writeTypedList(weather)
        parcel.writeParcelable(wind, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListData> {
        override fun createFromParcel(parcel: Parcel): ListData {
            return ListData(parcel)
        }

        override fun newArray(size: Int): Array<ListData?> {
            return arrayOfNulls(size)
        }
    }
}