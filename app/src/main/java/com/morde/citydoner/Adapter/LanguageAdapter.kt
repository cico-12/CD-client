package com.morde.citydoner.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morde.citydoner.databinding.ItemLanguageBinding
import com.morde.citydoner.Model.Language

class LanguageAdapter(
    private val context: Context,
    private val languageList: List<Language>,
    private val onLanguageSelected: (Language) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    inner class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = ItemLanguageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val language = languageList[position]
        holder.binding.textViewLanguage.text = language.name
        holder.binding.imageViewFlag.setImageResource(language.flagResId)

        holder.binding.root.setOnClickListener {
            onLanguageSelected(language)
        }
    }

    override fun getItemCount(): Int = languageList.size
}
