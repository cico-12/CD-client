package com.morde.citydoner.Model

import android.os.Parcel
import android.os.Parcelable

open class Item(
    val nameResourceId: Int,
    val description: Int,
    val price: Double,
    val imageResourceId: Int,
    val category: String,
    val addons: MutableList<Addon> = mutableListOf(),
    var isExpanded: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString() ?: "",
        mutableListOf<Addon>().apply {
            parcel.readTypedList(this, Addon.CREATOR)
        },
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(nameResourceId)
        parcel.writeInt(description)
        parcel.writeDouble(price)
        parcel.writeInt(imageResourceId)
        parcel.writeString(category)
        parcel.writeTypedList(addons)
        parcel.writeByte(if (isExpanded) 1 else 0)
    }

    override fun describeContents(): Int = 0

    fun getName(): String {
        return nameResourceId.toString()
    }


    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
