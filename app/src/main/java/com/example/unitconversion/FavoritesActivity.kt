package com.example.unitconversion

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.unitconversion.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityFavoritesBinding.inflate(LayoutInflater.from(this@FavoritesActivity))
        setContentView(binding.root)

        fun getLocale(list: MutableList<String>) {

//            val conf = resources.configuration
//            val locale = conf.locales[0]
//            createConfigurationContext(resources.configuration)
//            return if (cat) {
//                createConfigurationContext(conf).resources.getStringArray(R.array.categories)[id].lowercase()
//            } else {
//                createConfigurationContext(conf).resources.getStringArray(unitID)[id]
//            }
        }

        val prefs = getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        val names = mutableListOf<String>()

        for (p in prefs.all) {
            names.add(p.key.toString())
        }
        getLocale(names)

        binding.favoritesSelection.setOnItemClickListener { parent, view, position, id ->

            val element = parent.getItemAtPosition(position)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("favorite", true)
            intent.putExtra("data", element.toString())
            startActivity(intent)
        }

        val adapter = ArrayAdapter<String>(this, R.layout.list_items, names)
        binding.favoritesSelection.adapter = adapter

        binding.favoritesSelection.setOnTouchListener(object : OnSwipeListener(this@FavoritesActivity) {

            override fun onSwipeLeft() {
                val i = Intent(this@FavoritesActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivityIfNeeded(i, 0)
            }
        })
    }
}