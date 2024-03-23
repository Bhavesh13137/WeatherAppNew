package com.bhavesh.weatherappindianic.response

import android.os.Parcel
import android.os.Parcelable

data class SysX(
    val pod: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pod)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SysX> {
        override fun createFromParcel(parcel: Parcel): SysX {
            return SysX(parcel)
        }

        override fun newArray(size: Int): Array<SysX?> {
            return arrayOfNulls(size)
        }
    }
}