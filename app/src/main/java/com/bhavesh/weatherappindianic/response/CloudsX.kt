package com.bhavesh.weatherappindianic.response

import android.os.Parcel
import android.os.Parcelable

data class CloudsX(
    val all: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(all)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CloudsX> {
        override fun createFromParcel(parcel: Parcel): CloudsX {
            return CloudsX(parcel)
        }

        override fun newArray(size: Int): Array<CloudsX?> {
            return arrayOfNulls(size)
        }
    }
}