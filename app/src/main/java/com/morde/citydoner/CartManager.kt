package com.morde.citydoner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.morde.citydoner.Model.CartItem

object CartManager {
    private val cartItems = mutableListOf<CartItem>()
    var totalPrice: Double = 0.0
    private val _cartItemCount = MutableLiveData(0)
    val cartItemCount: LiveData<Int> get() = _cartItemCount



    fun addItem(item: CartItem) {
        var found = false

        for (cartItem in cartItems) {
            if (
                cartItem.nameResourceId == item.nameResourceId &&
                cartItem.category == item.category &&
                cartItem.addons.map { it.name }.sorted() == item.addons.map { it.name }.sorted()
            ) {
                cartItem.quantity += item.quantity
                found = true
                break
            }
        }
        if (!found) {
            cartItems.add(item)
        }
        updateTotalPrice()
        updateCartItemCount()
    }

    fun increaseItemQuantity(item: CartItem) {
        for (cartItem in cartItems) {
            if (
                cartItem.nameResourceId == item.nameResourceId &&
                cartItem.category == item.category &&
                cartItem.addons.map { it.name }.sorted() == item.addons.map { it.name }.sorted()
            ) {
                cartItem.quantity += 1
                updateTotalPrice()
                updateCartItemCount()
                return
            }
        }
    }


    fun getTotalItemCount(): Int {
        return cartItems.sumOf { it.quantity }
    }

    fun getCartItems(): List<CartItem> = cartItems


    private fun updateTotalPrice() {
        totalPrice = cartItems.sumOf { it.price * it.quantity }
    }

    fun clearCart(){
        cartItems.clear()
        totalPrice = 0.0
    }
    private fun updateCartItemCount() {
        _cartItemCount.postValue(cartItems.sumOf { it.quantity })
    }

    fun removeItem(item: CartItem) {
        val iterator = cartItems.iterator()
        while (iterator.hasNext()) {
            val existingItem = iterator.next()
            if (existingItem.nameResourceId == item.nameResourceId && existingItem.addons == item.addons) {
                existingItem.quantity -= 1

                if (existingItem.quantity <= 0) {
                    iterator.remove()
                }
                break
            }
        }
        updateTotalPrice()
        updateCartItemCount()
    }
}
