package com.morde.citydoner.Model

import android.os.Parcel
import android.os.Parcelable

class CartItem(
    nameResourceId: Int,
    description: Int,
    price: Double,
    imageResourceId: Int,
    category: String,
    var quantity: Int = 1,
    addons: MutableList<Addon>
) : Item(nameResourceId, description, price, imageResourceId, category, addons), Parcelable {

    fun getAddonNames(): List<String> {
        return addons.map { it.getAddonName() }
    }

    constructor() : this(0, 0, 0.0, 0, "", 1, mutableListOf())

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        mutableListOf<Addon>().apply { parcel.readTypedList(this, Addon.CREATOR) }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(quantity)
        parcel.writeTypedList(addons)
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
