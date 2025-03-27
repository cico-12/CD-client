package com.morde.citydoner.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morde.citydoner.Model.Item
import com.morde.citydoner.Adapter.ItemAdapter
import com.morde.citydoner.Model.Addon
import com.morde.citydoner.R

class MenuFragment : Fragment() {

    private var categoryKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryKey = it.getString(ARG_CATEGORY)
        }
    }

    companion object{
        private val ARG_CATEGORY = "category"

        fun newInstance(category: String): MenuFragment {
            val fragment = MenuFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val category = arguments?.getString(ARG_CATEGORY) ?: "doner"
        val items = getMenuItems(requireContext(), category)

        recyclerView.adapter = ItemAdapter(requireContext(), items)

        return view
    }

    private fun getMenuItems(context: Context, category: String): List<Item> {
        val addons = listOf(
            Addon(context.getString(R.string.kecap)),
            Addon(context.getString(R.string.majoneza)),
            Addon(context.getString(R.string.kupus)),
            Addon(context.getString(R.string.salata)),
            Addon(context.getString(R.string.blagi_sos)),
            Addon(context.getString(R.string.ljuti_sos)),
            Addon(context.getString(R.string.paradajz)),
            Addon(context.getString(R.string.chilli))
        )

        return when (category) {
            "doner" -> listOf(
                Item(R.string.item_doner_chicken, R.string.item_doner_chicken, 8.00, R.drawable.doner_chicken, "doner", addons.toMutableList()),
                Item(R.string.item_doner_juneci, R.string.item_doner_juneci, 8.00, R.drawable.doner_juneci, "doner", addons.toMutableList()),
                Item(R.string.item_doner_cheese, R.string.item_doner_cheese, 9.00, R.drawable.doner_cheese, "doner", addons.toMutableList()),
                Item(R.string.item_doner_box, R.string.item_doner_box, 10.00, R.drawable.doner_box, "doner", addons.toMutableList()),
                Item(R.string.item_doner_salad, R.string.item_doner_salad, 9.00, R.drawable.doner_salad, "doner", addons.toMutableList())
            )
            "pice" -> listOf(
                Item(R.string.item_gazirani, R.string.item_gazirani, 3.00, R.drawable.drink_sodas, "pice", mutableListOf()),
                Item(R.string.item_prirodna, R.string.item_prirodna, 2.00, R.drawable.drink_water, "pice", mutableListOf()),
                Item(R.string.item_mineralna, R.string.item_mineralna, 2.00, R.drawable.drink_sprinkling_water, "pice", mutableListOf()),
                Item(R.string.item_ayran, R.string.item_ayran, 2.00, R.drawable.drink_ayran, "pice", mutableListOf()),
                Item(R.string.item_prirodni, R.string.item_prirodni, 4.00, R.drawable.drink_natural_juice, "pice", mutableListOf())
            )
            "tortilla" -> listOf(
                Item(R.string.item_tortilja_pileća, R.string.item_tortilja_pileća, 7.00, R.drawable.tortilla_chicken, "tortilla", addons.toMutableList()),
                Item(R.string.item_tortilja_juneća, R.string.item_tortilja_juneća, 7.00, R.drawable.tortilla_juneca, "tortilla", addons.toMutableList())
            )
            "prilog" -> listOf(
                Item(R.string.item_pomfrit, R.string.item_pomfrit, 4.00, R.drawable.sides_fries, "prilog", mutableListOf()),
                Item(R.string.item_riza, R.string.item_riza, 3.00, R.drawable.sides_rice, "prilog", mutableListOf())
            )
            "grill" -> listOf(
                Item(R.string.item_hamburger, R.string.item_hamburger, 7.00, R.drawable.grill_hamburger, "grill", addons.toMutableList()),
                Item(R.string.item_cizburger, R.string.item_cizburger, 8.00, R.drawable.grill_cheseburger, "grill", addons.toMutableList()),
                Item(R.string.item_chicken_file, R.string.item_chicken_file, 8.00, R.drawable.grill_pileci_file, "grill", addons.toMutableList())
            )
            "plata" -> listOf(
                Item(R.string.item_plata_sarajevo, R.string.item_plata_sarajevo, 12.00, R.drawable.plata_sarajevo, "plata", addons.toMutableList()),
                Item(R.string.item_plata_berlin, R.string.item_plata_berlin, 12.00, R.drawable.plata_berlin, "plata", addons.toMutableList()),
                Item(R.string.item_plata_istanbul, R.string.item_plata_istanbul, 12.00, R.drawable.plata_istanbul, "plata", addons.toMutableList())
            )
            else -> emptyList()
        }
    }
}
