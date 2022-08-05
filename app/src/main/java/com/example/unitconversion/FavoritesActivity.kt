package com.example.unitconversion

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.unitconversion.databinding.ActivityFavoritesBinding
import java.util.*

class FavoritesActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val binding = ActivityFavoritesBinding.inflate(LayoutInflater.from(this@FavoritesActivity))
        val prefs = getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        setContentView(binding.root)

        fun getLocale(list: MutableList<String>): MutableList<String> {

            val conf = resources.configuration
            conf.setLocale(Locale.getDefault())
            val ctx = createConfigurationContext(conf)

            val localizedNames = mutableListOf<String>()

            for (item in list) {

                val split = item.split("-")
                val from = ctx.resources.getStringArray(split[0].toInt())[split[2].toInt()]
                val to = ctx.resources.getStringArray(split[0].toInt())[split[3].toInt()]

                val name = from.lowercase().replaceFirstChar { c -> c.uppercase() } +
                        " ->\n" +
                        to.lowercase().replaceFirstChar { c -> c.uppercase() }
                localizedNames.add(name)
            }
            return localizedNames
        }

        val keys = prefs.all.keys.toMutableList()
        val names = getLocale(keys)

        binding.favoritesSelection.setOnItemClickListener { _, _, position, _ ->

            val element = keys[position]
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("favorite", true)
            intent.putExtra("data", element.toString())
            startActivity(intent)
        }

        val adapter = ArrayAdapter(this, R.layout.list_items, names)
        binding.favoritesSelection.adapter = adapter

        binding.favoritesSelection.setOnTouchListener(object :
            OnSwipeListener(this@FavoritesActivity) {

            override fun onSwipeLeft() {
                val i = Intent(this@FavoritesActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivityIfNeeded(i, 0)
            }
        })
    }
}