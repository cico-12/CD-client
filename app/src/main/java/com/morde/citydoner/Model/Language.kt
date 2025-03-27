package com.morde.citydoner.Model

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

data class Language(
    val name: String,
    val flagResId: Int,
    val localeCode: String
) {
    companion object {
        fun setLocale(context: Context, localeCode: String) {
            val locale = Locale(localeCode)
            Locale.setDefault(locale)

            val config = Configuration()
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)

            val preferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            preferences.edit().putString("languageCode", localeCode).apply()
        }
    }
}