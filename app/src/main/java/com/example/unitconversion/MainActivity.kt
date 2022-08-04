package com.example.unitconversion

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unitconversion.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedFromPrev = 0
    private var selectedFromCur = 0
    private var selectedToPrev = 0
    private var selectedToCur = 0
    private var unitID = R.array.length

    private companion object Converter {

        private fun convert(input: BigDecimal, from: String, to: String): String {

            if (from == to || input == BigDecimal.ZERO) {
                return input.stripTrailingZeros().toPlainString()
            }

            val styledFrom = getEnum(styleString(from))
            val styledTo = getEnum(styleString(to))

            val toReturn =
                input.stripTrailingZeros()
                    .multiply(styledFrom).stripTrailingZeros()
                    .divide((styledTo), 20, BigDecimal.ROUND_HALF_UP)
                    .stripTrailingZeros()
            return if (toReturn.movePointRight(3) >= BigDecimal.ONE)
                toReturn.setScale(3, BigDecimal.ROUND_HALF_UP)
                    .stripTrailingZeros()
                    .toPlainString()
            else toReturn
                .setScale(20, BigDecimal.ROUND_HALF_UP)
                .stripTrailingZeros()
                .toPlainString()
        }

        private fun getEnum(enum: String): BigDecimal = Enums.valueOf(enum).value

        private fun styleString(str: String): String {

            fun style(str: String, c: String): String {
                val split = str.split(c)
                val sb = StringBuilder()
                for (s in split) {
                    sb.append(s + "_")
                }
                sb.deleteCharAt(sb.lastIndex)
                return sb.toString()
            }

            return if (str.contains(" ")) {
                style(str, " ")
            } else if (str.contains("-"))
                style(str, "-")
            else str
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val prefs =
            getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        var launchedFromIntent = intent.getBooleanExtra("favorite", false)

        fun changeAdapter(resource: Int, unit: Boolean) {

            if (unit) {
                ArrayAdapter.createFromResource(
                    this,
                    R.array.categories,
                    R.layout.spinner_item
                )
                    .also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.unitSelection.adapter = adapter

                        if (Locale.getDefault().language != "en") {
                            val param =
                                binding.unitSelection.layoutParams as ViewGroup.MarginLayoutParams
                            param.setMargins(0, 125, 0, 0)
                            binding.unitSelection.layoutParams = param
                        }
                    }
            } else {
                ArrayAdapter.createFromResource(
                    this,
                    resource,
                    android.R.layout.simple_spinner_item
                )
                    .also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.convertFrom.adapter = adapter
                        binding.convertTo.adapter = adapter
                    }
            }
        }

        fun getLocale(id: Int, cat: Boolean): String {

            val conf = resources.configuration
            conf.setLocale(Locale("en"))

            return if (cat) {
                createConfigurationContext(conf).resources.getStringArray(R.array.categories)[id].lowercase()
            } else {
                createConfigurationContext(conf).resources.getStringArray(unitID)[id]
            }
        }

        fun resetFilter() {
            binding.editField.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
        }

        fun convertOnUnitChange() {

            if (binding.convertFrom.selectedItem == binding.convertTo.selectedItem) {
                binding.convertFrom.setSelection(selectedToPrev)
            }
            if (binding.resultField.text != "") {
                binding.resultField.text = convert(
                    BigDecimal(binding.editField.text.toString()),
                    getLocale(binding.convertFrom.selectedItemPosition, false),
                    getLocale(binding.convertTo.selectedItemPosition, false),
                )
            }
        }

        fun createSPName(): String {

            val unitPos =
                binding.unitSelection.adapter.getItemId(binding.unitSelection.selectedItemPosition)
            return "$unitID" +
                    "-${unitPos}" +
                    "-${binding.convertFrom.selectedItemId}" +
                    "-${binding.convertTo.selectedItemId}"
        }

        fun controlStar() {

            val name = createSPName()

            if (!prefs.contains(name)) {
                binding.favorites.setImageResource(R.drawable.star)
            } else {
                binding.favorites.setImageResource(R.drawable.star_pressed)
            }
        }

        changeAdapter(unitID, true)

        binding.unitSelection.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    resetFilter()
                    unitID =
                        resources.getIdentifier(
                            getLocale(p3.toInt(), true),
                            "array",
                            packageName
                        )
                    selectedToPrev = 0
                    selectedToCur = 0
                    selectedFromCur = 0
                    selectedFromCur = 0
                    changeAdapter(unitID, false)

                    if (launchedFromIntent) {

                        val split = intent.getStringExtra("data")!!.split("-")
                        binding.unitSelection.setSelection(split[1].toInt())
                        binding.convertFrom.setSelection(split[2].toInt())
                        binding.convertTo.setSelection(split[3].toInt())
                    }

                    binding.editField.text.clear()
                    binding.resultField.clearComposingText()
                    controlStar()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.convertFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    launchedFromIntent = false
                    resetFilter()
                    selectedFromPrev = selectedFromCur
                    selectedFromCur = binding.convertFrom.selectedItemPosition
                    convertOnUnitChange()
                    controlStar()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.convertTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    resetFilter()
                    selectedToPrev = selectedToCur
                    selectedToCur = binding.convertTo.selectedItemPosition
                    convertOnUnitChange()
                    controlStar()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.editField.addTextChangedListener(
            object : TextWatcher {

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.isNullOrBlank()) binding.resultField.text = ""
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    if (!p0!!.matches("^\\d+\\.?\\d*\$".toRegex())) binding.resultField.text = ""
                    else {
                        val text = convert(
                            BigDecimal(p0.toString()),
                            getLocale(binding.convertFrom.selectedItemPosition, false),
                            getLocale(binding.convertTo.selectedItemPosition, false),
                        )
                        if (text.length >= 40) {
                            binding.editField.filters =
                                arrayOf<InputFilter>(InputFilter.LengthFilter(binding.editField.text.length))
                            Toast.makeText(
                                applicationContext,
                                R.string.maxValue,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        binding.resultField.text = text
                    }
                }
            })

        binding.favorites.setOnClickListener {

            if (prefs.all.isEmpty())
                Toast.makeText(this, R.string.swipeHint, Toast.LENGTH_SHORT).show()

            val name = createSPName()
            if (!prefs.contains(name)) {
                prefs.edit().putBoolean(name, false).apply()
                binding.favorites.setImageResource(R.drawable.star_pressed)
            } else {
                prefs.edit().remove(name).apply()
                binding.favorites.setImageResource(R.drawable.star)
            }
        }

        binding.layout.setOnTouchListener(
            object : OnSwipeListener(this@MainActivity) {

                override fun onSwipeRight() {
                    startActivity(Intent(this@MainActivity, FavoritesActivity::class.java))
                }
            })
    }
}