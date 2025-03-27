package com.morde.citydoner.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morde.citydoner.Model.Addon
import com.morde.citydoner.databinding.AddonItemBinding

class AddonAdapter(private val addons: List<Addon>) :
    RecyclerView.Adapter<AddonAdapter.AddonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonViewHolder {
        val binding = AddonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddonViewHolder, position: Int) {
        val addon = addons[position]
        holder.bind(addon)
    }

    override fun getItemCount(): Int {
        return addons.size
    }

    inner class AddonViewHolder(private val binding: AddonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(addon: Addon) {
            binding.checkboxAddon.text = addon.name
            binding.checkboxAddon.isChecked = addon.isSelected

            binding.checkboxAddon.setOnCheckedChangeListener { _, isChecked ->
                addon.isSelected = isChecked
            }
        }
    }
}

