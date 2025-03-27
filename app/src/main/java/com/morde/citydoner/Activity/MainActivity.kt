package com.morde.citydoner.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.morde.citydoner.Adapter.CategoryAdapter
import com.morde.citydoner.CartManager
import com.morde.citydoner.Fragment.MenuFragment
import com.morde.citydoner.Model.Language
import com.morde.citydoner.R
import com.morde.citydoner.Model.Category
import com.morde.citydoner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val languageCode = preferences.getString("languageCode", "en") ?: "en"
        Language.setLocale(this, languageCode)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MenuFragment())
                .commit()
        }

        updateCartItemCount()

        binding.restartId.setOnClickListener {
            CartManager.clearCart()
            Language.setLocale(this, "en")
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }

        binding.cartId.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        setupCategorySlider()


        CartManager.cartItemCount.observe(this, Observer { count ->
            updateCartItemCount()
        })
    }

    private fun updateCartItemCount() {
        val totalItems = CartManager.getTotalItemCount()

        if (totalItems > 0) {
            binding.cartItemCount.text = totalItems.toString()
            binding.cartItemCount.visibility = View.VISIBLE
        } else {
            binding.cartItemCount.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        updateCartItemCount()
    }

    private fun setupCategorySlider() {
        val categories = listOf(
            Category(getString(R.string.main_doner_title), R.drawable.category_doner),
            Category(getString(R.string.tortilja_title), R.drawable.category_tortilla),
            Category(getString(R.string.rostilj_title), R.drawable.category_burger),
            Category(getString(R.string.plata_title), R.drawable.category_plata),
            Category(getString(R.string.prilog_title), R.drawable.category_prilog),
            Category(getString(R.string.pice_title), R.drawable.category_pice)
        )

        val categoryAdapter = CategoryAdapter(categories) { categoryName ->
            val categoryKey = when (categoryName) {
                getString(R.string.main_doner_title) -> "doner"
                getString(R.string.tortilja_title) -> "tortilla"
                getString(R.string.rostilj_title) -> "grill"
                getString(R.string.plata_title) -> "plata"
                getString(R.string.prilog_title) -> "prilog"
                getString(R.string.pice_title) -> "pice"
                else -> "doner"
            }
            openFragment(MenuFragment.newInstance(categoryKey))
        }

        binding.categoryMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
