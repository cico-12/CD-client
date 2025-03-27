package com.morde.citydoner.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morde.citydoner.CartManager
import com.morde.citydoner.Model.CartItem

class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData(CartManager.getCartItems())
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _totalPrice = MutableLiveData(CartManager.totalPrice)
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun increaseItemQuantity(item : CartItem){
        CartManager.increaseItemQuantity(item)
        updateCart()
    }

    fun removeItem(item: CartItem) {
        CartManager.removeItem(item)
        updateCart()
    }
    fun clearCart() {
        CartManager.clearCart()
        updateCart()
    }

    fun updateCart() {
        _cartItems.value = CartManager.getCartItems()
        _totalPrice.value = CartManager.totalPrice
    }

    fun getItemName(context: Context, item: CartItem): String {
        return context.getString(item.nameResourceId)
    }

    fun getItemDescription(context: Context, item: CartItem): String {
        return context.getString(item.description)
    }
}
