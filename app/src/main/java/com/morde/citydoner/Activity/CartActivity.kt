package com.morde.citydoner.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.morde.citydoner.Adapter.CartAdapter
import com.morde.citydoner.Model.CartItem
import com.morde.citydoner.R
import com.morde.citydoner.databinding.ActivityCartBinding
import com.morde.citydoner.ViewModel.CartViewModel

class CartActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeCart()

        binding.backButton.setOnClickListener {
            finish()
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders")

        binding.placeOrderButton.setOnClickListener {
            val cartItems = cartViewModel.cartItems.value ?: emptyList()

            if (cartItems.isNotEmpty()) {
                saveOrderToFirebase(cartItems)
            } else {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(this, mutableListOf(), cartViewModel)
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCart.adapter = cartAdapter
    }

    @SuppressLint("StringFormatInvalid", "StringFormatMatches")
    private fun observeCart() {
        cartViewModel.cartItems.observe(this) { cartItems ->
            cartAdapter.updateItems(cartItems)
        }
        cartViewModel.totalPrice.observe(this) { totalPrice ->
            binding.totalPriceTextView.text = getString(R.string.cart_total, totalPrice)
        }
    }

    private fun generateOrderId(): Int {
        return (System.currentTimeMillis() % 10000).toInt()
    }

    private fun saveOrderToFirebase(cartItems: List<CartItem>) {
        val orderId = generateOrderId()
        val filteredCartItems = cartItems.map { cartItem ->
            mapOf(
                "name" to getString(cartItem.nameResourceId),
                "price" to cartItem.price,
                "quantity" to cartItem.quantity,
                "addons" to cartItem.getAddonNames()
            )
        }

        val orderData = mapOf(
            "orderId" to orderId,
            "items" to filteredCartItems
        )

        databaseReference.child(orderId.toString()).setValue(orderData)
            .addOnSuccessListener {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                cartViewModel.clearCart()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to place order!", Toast.LENGTH_SHORT).show()
            }
    }



}