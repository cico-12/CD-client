package com.morde.citydoner.Activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.morde.citydoner.Adapter.LanguageAdapter
import com.morde.citydoner.R
import com.morde.citydoner.databinding.ActivityIntroBinding
import android.content.Intent
import com.morde.citydoner.Model.Language

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private var selectedLanguageCode: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewSuggest.layoutManager = GridLayoutManager(this, 3)

        val languageList = listOf(
            Language("English", R.drawable.flag_uk, "en"),
            Language("Turkish", R.drawable.flag_turkie, "tr"),
            Language("Bosnian", R.drawable.flag_bosnia, "bs"),
            Language("German", R.drawable.flag_germany, "de"),
            Language("Arabic", R.drawable.flag_saudi, "ar"),
            Language("Spanish", R.drawable.flag_spain, "es")
        )

        val adapter = LanguageAdapter(this, languageList) { selectedLanguage ->
            Language.setLocale(this, selectedLanguage.localeCode)
            recreate()
        }

        binding.recyclerViewSuggest.adapter = adapter

        binding.button2.setOnClickListener {
            val preferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val selectedLanguageCode = preferences.getString("languageCode", "en") ?: "en"

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("LANGUAGE_CODE", selectedLanguageCode) // Pass language
            startActivity(intent)
            finish()
        }

    }
}