package com.morde.citydoner.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.morde.citydoner.databinding.ItemCartBinding
import com.morde.citydoner.Model.CartItem
import com.morde.citydoner.R
import com.morde.citydoner.ViewModel.CartViewModel

class CartAdapter(
    private val context: Context,
    private var cartItems: MutableList<CartItem>,
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            val itemName = cartViewModel.getItemName(context, item)
            val itemDescription = cartViewModel.getItemDescription(context, item)

            binding.itemName.text = itemName
            binding.itemDesc.text = itemDescription
            binding.itemPrice.text = "${item.price} KM"
            binding.quantityTextView.text = item.quantity.toString()
            binding.itemImage.setImageResource(item.imageResourceId)


            binding.itemAddons.text = if (item.addons.isNotEmpty()) {
                "${binding.root.context.getString(R.string.prilozi)} ${item.addons.joinToString(", ") { it.name }}"
            } else {
                "${binding.root.context.getString(R.string.bez_priloga)}"
            }

            binding.btnDeleteCart.setOnClickListener {
                cartViewModel.removeItem(item)
                notifyDataSetChanged()
                Toast.makeText(context, "Item removed from cart!", Toast.LENGTH_SHORT).show()
            }
            binding.btnAddCart.setOnClickListener {
                cartViewModel.increaseItemQuantity(item)
                notifyDataSetChanged()
                Toast.makeText(context, "Item quantity increased!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateItems(newItems: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyDataSetChanged()
    }
}
