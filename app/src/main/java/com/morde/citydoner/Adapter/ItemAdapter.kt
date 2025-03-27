package com.morde.citydoner.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morde.citydoner.CartManager
import com.morde.citydoner.Model.CartItem
import com.morde.citydoner.Model.Item
import com.morde.citydoner.databinding.ItemLayoutBinding

class ItemAdapter(private val context: Context, private val itemList: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var expandedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, position: Int) {
            binding.itemName.setText(item.nameResourceId)
            binding.itemPrice.text = "${item.price} KM"
            binding.itemDesc.setText(item.description)
            binding.itemImage.setImageResource(item.imageResourceId)

            val isExpanded = position == expandedPosition
            binding.addonsLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                if (expandedPosition == position) {
                    expandedPosition = RecyclerView.NO_POSITION
                } else {
                    val previousExpanded = expandedPosition
                    expandedPosition = position
                    notifyItemChanged(previousExpanded)
                }
                notifyItemChanged(position)
            }

            val addonsCopy = item.addons.map { it.copy() }.toMutableList()
            binding.recyclerViewAddons.layoutManager = LinearLayoutManager(context)
            binding.recyclerViewAddons.adapter = AddonAdapter(addonsCopy)

            binding.btnAddToCart.setOnClickListener {
                val selectedAddons = addonsCopy.filter { it.isSelected }
                val cartItem = CartItem(
                    item.nameResourceId,
                    item.description,
                    item.price,
                    item.imageResourceId,
                    item.category,
                    1,
                    selectedAddons.toMutableList()
                )
                CartManager.addItem(cartItem)
                addonsCopy.forEach { it.isSelected = false }
                if (expandedPosition == position) {
                    expandedPosition = RecyclerView.NO_POSITION
                }
                notifyItemChanged(position)
            }
        }
    }
}
