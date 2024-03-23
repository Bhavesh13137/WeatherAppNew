package com.bhavesh.weatherappindianic.response

import android.os.Parcel
import android.os.Parcelable

data class ForecastResponse(
    val city: City? = null,
    val cnt: Int? = null,
    val cod: String? = null,
    val list: List<ListData>? = null,
    val message: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(City::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.createTypedArrayList(ListData),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(city, flags)
        parcel.writeValue(cnt)
        parcel.writeString(cod)
        parcel.writeTypedList(list)
        parcel.writeValue(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForecastResponse> {
        override fun createFromParcel(parcel: Parcel): ForecastResponse {
            return ForecastResponse(parcel)
        }

        override fun newArray(size: Int): Array<ForecastResponse?> {
            return arrayOfNulls(size)
        }
    }
}