package com.morde.citydoner.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morde.citydoner.Model.Category
import com.morde.citydoner.R
import com.morde.citydoner.databinding.CategoryItemBinding

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = -1

    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val category = categoryList[position]
        holder.binding.textViewCategoryName.text = category.name
        holder.binding.imageViewCategory.setImageResource(category.imageResId)

        if (position == selectedPosition) {
            holder.binding.donerCategory.setBackgroundResource(R.drawable.selected_category)
        } else {
            holder.binding.donerCategory.setBackgroundResource(R.drawable.category_backgroung_border)
        }

        holder.binding.root.setOnClickListener {
            val previousSelected = selectedPosition
            selectedPosition = position

            notifyItemChanged(previousSelected)
            notifyItemChanged(selectedPosition)

            onCategoryClick(category.name)
        }
    }

    override fun getItemCount(): Int = categoryList.size
}