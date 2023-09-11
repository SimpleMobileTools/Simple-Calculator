package com.simplemobiletools.calculator.views

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.TextViewCompat
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.databinding.ViewConverterBinding
import com.simplemobiletools.calculator.helpers.COMMA
import com.simplemobiletools.calculator.helpers.DOT
import com.simplemobiletools.calculator.helpers.NumberFormatHelper
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.commons.dialogs.RadioGroupDialog
import com.simplemobiletools.commons.extensions.applyColorFilter
import com.simplemobiletools.commons.extensions.getProperPrimaryColor
import com.simplemobiletools.commons.extensions.getProperTextColor
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

        binding.topUnitName.setClickListenerForUnitSelector(::topUnit, ::bottomUnit)
        binding.bottomUnitName.setClickListenerForUnitSelector(::bottomUnit, ::topUnit)

        updateColors()
    }

    fun setConverter(converter: Converter) {
        this.converter = converter
        topUnit = converter.defaultTopUnit
        bottomUnit = converter.defaultBottomUnit

        binding.topUnitText.text = "1"
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

        binding.swapButton.applyColorFilter(context.getProperPrimaryColor())
    }

    fun clear() {
        binding.topUnitText.text = "0"
        binding.bottomUnitText.text = "0"
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
        var value = binding.topUnitText.text.toString()
        if (!value.contains(decimalSeparator)) {
            when (value) {
                "0" -> value = "0$decimalSeparator"
                "" -> value += "0$decimalSeparator"
                else -> value += decimalSeparator
            }

            binding.topUnitText.text = value
        }
    }

    private fun zeroClicked() {
        val value = binding.topUnitText.text
        if (value != "0" || value.contains(decimalSeparator)) {
            addDigit(0)
        }
    }

    private fun addDigit(digit: Int) {
        var value = binding.topUnitText.text.toString()
        if (value == "0") {
            value = ""
        }

        value += digit
        value = formatter.addGroupingSeparators(value)
        binding.topUnitText.text = value
    }

    private fun switch() {
        ::topUnit.swapWith(::bottomUnit)
        updateBottomValue()
        updateUnitLabels()
    }

    private fun updateUnitLabels() {
        binding.topUnitName.text = topUnit?.nameResId?.let { context.getString(it) }
        binding.bottomUnitName.text = bottomUnit?.nameResId?.let { context.getString(it) }
    }

    private fun updateBottomValue() {
        converter?.apply {
            val converted = convert(topUnit!!.withValue(formatter.removeGroupingSeparator(binding.topUnitText.text.toString()).toDouble()), bottomUnit!!).value
            binding.bottomUnitText.text = formatter.doubleToString(converted)
        }
    }

    fun updateSeparators(decimalSeparator: String, groupingSeparator: String) {
        if (this.decimalSeparator != decimalSeparator || this.groupingSeparator != groupingSeparator) {
            this.decimalSeparator = decimalSeparator
            this.groupingSeparator = groupingSeparator
            formatter.decimalSeparator = decimalSeparator
            formatter.groupingSeparator = groupingSeparator
            binding.topUnitText.text = "0"
            binding.bottomUnitText.text = "0"
        }
    }

    private fun <T> KMutableProperty0<T>.swapWith(other: KMutableProperty0<T>) {
        this.get().also {
            this.set(other.get())
            other.set(it)
        }
    }

    private fun View.setClickListenerForUnitSelector(propertyToChange: KMutableProperty0<Converter.Unit?>, otherProperty: KMutableProperty0<Converter.Unit?>) {
        setOnClickListener {
            val items = ArrayList(converter!!.units.mapIndexed { index, unit ->
                RadioItem(index, context.getString(unit.nameResId), unit)
            })
            RadioGroupDialog(context as Activity, items) {
                val unit = it as Converter.Unit
                if (unit == otherProperty.get()) {
                    switch()
                } else if (unit != propertyToChange.get()) {
                    propertyToChange.set(unit)
                    updateBottomValue()
                }
                updateUnitLabels()
            }
        }
    }
}
