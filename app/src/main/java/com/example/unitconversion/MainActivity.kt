package com.example.unitconversion

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
    private var unitId = R.array.length

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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        fun changeAdapter(resource: Int) {

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

        fun getLocale(id: Int, cat: Boolean): String {

            val conf = resources.configuration
            conf.setLocale(Locale("en"))

            return if (cat) {
                createConfigurationContext(conf).resources.getStringArray(R.array.categories)[id].lowercase()
            } else {
                createConfigurationContext(conf).resources.getStringArray(unitId)[id]
            }
        }

        fun resetFilter() {
            binding.editField.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
        }

        changeAdapter(R.array.time)
        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            R.layout.spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.unitSelection.adapter = adapter

                if (Locale.getDefault().language != "en") {
                    val param = binding.unitSelection.layoutParams as ViewGroup.MarginLayoutParams
                    param.setMargins(0, 125, 0, 0)
                    binding.unitSelection.layoutParams = param
                }
            }

        binding.unitSelection.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    resetFilter()
                    val id =
                        resources.getIdentifier(getLocale(p3.toInt(), true), "array", packageName)
                    unitId = id
                    selectedToPrev = 0
                    selectedToCur = 0
                    selectedFromCur = 0
                    selectedFromCur = 0
                    changeAdapter(id)
                    binding.editField.text.clear()
                    binding.resultField.clearComposingText()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }



        binding.convertFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                resetFilter()
                selectedFromPrev = selectedFromCur
                selectedFromCur = binding.convertFrom.selectedItemPosition
                if (binding.convertFrom.selectedItemPosition == binding.convertTo.selectedItemPosition) {
                    binding.convertTo.setSelection(selectedFromPrev)
                }
                if (binding.resultField.text != "") {
                    binding.resultField.text = convert(
                        BigDecimal(binding.editField.text.toString()),
                        getLocale(binding.convertFrom.selectedItemPosition, false),
                        getLocale(binding.convertTo.selectedItemPosition, false),
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.convertTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                resetFilter()
                selectedToPrev = selectedToCur
                selectedToCur = binding.convertTo.selectedItemPosition
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

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.editField.addTextChangedListener(object : TextWatcher {

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
                        Toast.makeText(applicationContext, R.string.maxValue, Toast.LENGTH_SHORT)
                            .show()
                    }
                    binding.resultField.text = text
                }
            }
        })
    }
}