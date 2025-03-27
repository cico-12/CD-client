package com.morde.citydoner.Model

import android.os.Parcel
import android.os.Parcelable

data class Addon(
    val name: String,
    var isSelected: Boolean = false
) : Parcelable {

    fun getAddonName(): String {
        return name
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Addon> {
        override fun createFromParcel(parcel: Parcel): Addon {
            return Addon(parcel)
        }

        override fun newArray(size: Int): Array<Addon?> {
            return arrayOfNulls(size)
        }
    }
}