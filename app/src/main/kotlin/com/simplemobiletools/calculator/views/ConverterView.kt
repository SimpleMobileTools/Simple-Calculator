package com.simplemobiletools.calculator.views

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.databinding.ViewConverterBinding
import com.simplemobiletools.calculator.helpers.COMMA
import com.simplemobiletools.calculator.helpers.DOT
import com.simplemobiletools.calculator.helpers.NumberFormatHelper
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.calculator.helpers.converters.ValueWithUnit
import com.simplemobiletools.commons.dialogs.RadioGroupDialog
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.models.RadioItem
import me.grantland.widget.AutofitHelper
import kotlin.reflect.KMutableProperty0

class ConverterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private lateinit var binding: ViewConverterBinding

    private var converter: Converter? = null
    private var topUnit: Converter.Unit? = null
    private var bottomUnit: Converter.Unit? = null

    private var decimalSeparator: String = DOT
    private var groupingSeparator: String = COMMA
    private val formatter = NumberFormatHelper(
        decimalSeparator = decimalSeparator, groupingSeparator = groupingSeparator
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewConverterBinding.bind(this)

        AutofitHelper.create(binding.topUnitText)
        AutofitHelper.create(binding.bottomUnitText)

        binding.swapButton.setOnClickListener { switch() }

        binding.topUnitHolder.setClickListenerForUnitSelector(::topUnit, ::bottomUnit)
        binding.bottomUnitHolder.setClickListenerForUnitSelector(::bottomUnit, ::topUnit)

        updateColors()
    }

    fun setConverter(converter: Converter) {
        this.converter = converter
        topUnit = converter.defaultTopUnit
        bottomUnit = converter.defaultBottomUnit

        binding.topUnitText.setFormattedUnitText(topUnit!!.withValue(0.0))
        updateBottomValue()
        updateUnitLabels()
    }

    fun updateColors() {
        binding.topUnitText.setTextColor(context.getProperTextColor())
        binding.bottomUnitText.setTextColor(context.getProperTextColor())
        binding.topUnitName.setTextColor(context.getProperTextColor())
        binding.bottomUnitName.setTextColor(context.getProperTextColor())
        TextViewCompat.setCompoundDrawableTintList(
            binding.topUnitName,
            ColorStateList.valueOf(context.getProperPrimaryColor())
        )
        TextViewCompat.setCompoundDrawableTintList(
            binding.bottomUnitName,
            ColorStateList.valueOf(context.getProperPrimaryColor())
        )
        binding.topUnitHolder.setBackgroundColor(context.getProperBackgroundColor().lightenColor())

        binding.swapButton.applyColorFilter(context.getProperPrimaryColor())
    }

    fun clear() {
        binding.topUnitText.setFormattedUnitText(topUnit!!.withValue(0.0))
        binding.bottomUnitText.setFormattedUnitText(bottomUnit!!.withValue(0.0))
    }

    fun numpadClicked(id: Int) {
        when (id) {
            R.id.btn_decimal -> decimalClicked()
            R.id.btn_0 -> zeroClicked()
            R.id.btn_1 -> addDigit(1)
            R.id.btn_2 -> addDigit(2)
            R.id.btn_3 -> addDigit(3)
            R.id.btn_4 -> addDigit(4)
            R.id.btn_5 -> addDigit(5)
            R.id.btn_6 -> addDigit(6)
            R.id.btn_7 -> addDigit(7)
            R.id.btn_8 -> addDigit(8)
            R.id.btn_9 -> addDigit(9)
        }

        updateBottomValue()
    }

    private fun decimalClicked() {
        var value = binding.topUnitText.text.removeUnit()
        if (!value.contains(decimalSeparator)) {
            when (value) {
                "0" -> value = "0$decimalSeparator"
                "" -> value += "0$decimalSeparator"
                else -> value += decimalSeparator
            }

            binding.topUnitText.text = topUnit?.format(context, value)
        }
    }

    private fun zeroClicked() {
        val value = binding.topUnitText.text.removeUnit()
        if (value != "0" || value.contains(decimalSeparator)) {
            addDigit(0)
        }
    }

    private fun addDigit(digit: Int) {
        var value = binding.topUnitText.text.removeUnit()
        if (value == "0") {
            value = ""
        }

        value += digit
        value = formatter.addGroupingSeparators(value)
        binding.topUnitText.text = topUnit!!.format(context, value)
    }

    private fun switch() {
        ::topUnit.swapWith(::bottomUnit)
        updateTopValue()
        updateBottomValue()
        updateUnitLabels()
    }

    private fun updateUnitLabels() {
        binding.topUnitName.text = topUnit?.nameResId?.let { context.getString(it) }
        binding.bottomUnitName.text = bottomUnit?.nameResId?.let { context.getString(it) }
    }

    private fun updateTopValue() {
        binding.topUnitText.setFormattedUnitText(topUnit!!.withValue(binding.topUnitText.getValue()))
    }

    private fun updateBottomValue() {
        converter?.apply {
            val converted = convert(topUnit!!.withValue(binding.topUnitText.getValue()), bottomUnit!!).value
            binding.bottomUnitText.setFormattedUnitText(bottomUnit!!.withValue(converted))
        }
    }

    fun updateSeparators(decimalSeparator: String, groupingSeparator: String) {
        if (this.decimalSeparator != decimalSeparator || this.groupingSeparator != groupingSeparator) {
            this.decimalSeparator = decimalSeparator
            this.groupingSeparator = groupingSeparator
            formatter.decimalSeparator = decimalSeparator
            formatter.groupingSeparator = groupingSeparator
            binding.topUnitText.setFormattedUnitText(topUnit!!.withValue(0.0))
            binding.bottomUnitText.setFormattedUnitText(bottomUnit!!.withValue(0.0))
        }
    }

    private fun TextView.setFormattedUnitText(value: ValueWithUnit<*>) {
        text = value.unit.format(context, formatter.doubleToString(value.value))
    }

    private fun TextView.getValue(): Double {
        return formatter.removeGroupingSeparator(text.removeUnit()).toDouble()
    }

    private fun CharSequence.removeUnit() = filter { it.isDigit() || it == '.' || it == ',' }.toString()

    private fun <T> KMutableProperty0<T>.swapWith(other: KMutableProperty0<T>) {
        this.get().also {
            this.set(other.get())
            other.set(it)
        }
    }

    private fun View.setClickListenerForUnitSelector(propertyToChange: KMutableProperty0<Converter.Unit?>, otherProperty: KMutableProperty0<Converter.Unit?>) {
        setOnClickListener {
            val items = ArrayList(converter!!.units.mapIndexed { index, unit ->
                RadioItem(index, unit.getNameWithSymbol(context), unit)
            })
            RadioGroupDialog(context as Activity, items, converter!!.units.indexOf(propertyToChange.get())) {
                val unit = it as Converter.Unit
                if (unit == otherProperty.get()) {
                    switch()
                } else if (unit != propertyToChange.get()) {
                    propertyToChange.set(unit)
                    updateTopValue()
                    updateBottomValue()
                }
                updateUnitLabels()
            }
        }
    }
}
