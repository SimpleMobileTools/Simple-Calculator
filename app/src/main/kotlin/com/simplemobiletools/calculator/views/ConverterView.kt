package com.simplemobiletools.calculator.views

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.databinding.ViewConverterBinding
import com.simplemobiletools.calculator.helpers.COMMA
import com.simplemobiletools.calculator.helpers.DOT
import com.simplemobiletools.calculator.helpers.NumberFormatHelper
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.commons.dialogs.RadioGroupDialog
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.MEDIUM_ALPHA_INT
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
        AutofitHelper.create(binding.topUnitSymbol)
        AutofitHelper.create(binding.bottomUnitSymbol)

        binding.swapButton.setOnClickListener { switch() }

        binding.topUnitHolder.setClickListenerForUnitSelector(::topUnit, ::bottomUnit)
        binding.bottomUnitHolder.setClickListenerForUnitSelector(::bottomUnit, ::topUnit)
        binding.topUnitHolder.setOnLongClickListener {
            context.copyToClipboard(binding.topUnitText.text.toString())
            true
        }
        binding.bottomUnitHolder.setOnLongClickListener {
            context.copyToClipboard(binding.bottomUnitText.text.toString())
            true
        }

        updateColors()
    }

    fun setConverter(converter: Converter) {
        this.converter = converter
        topUnit = converter.defaultTopUnit
        bottomUnit = converter.defaultBottomUnit

        binding.topUnitText.text = "0"
        updateBottomValue()
        updateUnitLabelsAndSymbols()
    }

    fun updateColors() {
        listOf(binding.topUnitText, binding.bottomUnitText, binding.topUnitName, binding.bottomUnitName).forEach {
            it.setTextColor(context.getProperTextColor())
        }
        listOf(binding.topUnitName, binding.bottomUnitName).forEach {
            TextViewCompat.setCompoundDrawableTintList(
                it,
                ColorStateList.valueOf(context.getProperPrimaryColor())
            )
        }

        binding.topUnitHolder.setBackgroundColor(context.getProperBackgroundColor().lightenColor())
        binding.swapButton.applyColorFilter(context.getProperPrimaryColor())

        val drawable = ResourcesCompat.getDrawable(
            resources, com.simplemobiletools.commons.R.drawable.pill_background, context.theme
        )?.constantState?.newDrawable()?.mutate() as RippleDrawable
        val bgLayerList = drawable.findDrawableByLayerId(com.simplemobiletools.commons.R.id.button_pill_background_holder) as LayerDrawable
        val bgLayer = bgLayerList.findDrawableByLayerId(com.simplemobiletools.commons.R.id.button_pill_background_shape) as GradientDrawable
        bgLayer.cornerRadius = context.resources.getDimension(com.simplemobiletools.commons.R.dimen.rounded_corner_radius_big)
        listOf(binding.topUnitSymbol, binding.bottomUnitSymbol).forEach {
            it.background = drawable
            it.background?.alpha = MEDIUM_ALPHA_INT
        }
    }

    fun clear() {
        binding.topUnitText.text = "0"
        binding.bottomUnitText.text = "0"
    }

    fun deleteCharacter() {
        var newValue = binding.topUnitText.text.dropLast(1)
        newValue = newValue.trimEnd(groupingSeparator.single())
        if (newValue == "") {
            newValue = "0"
        }

        val value = formatter.removeGroupingSeparator(newValue.toString()).toDouble()
        binding.topUnitText.text = formatter.doubleToString(value)

        updateBottomValue()
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
        updateUnitLabelsAndSymbols()
    }

    private fun updateUnitLabelsAndSymbols() {
        binding.topUnitName.text = topUnit?.nameResId?.let { context.getString(it) }
        binding.bottomUnitName.text = bottomUnit?.nameResId?.let { context.getString(it) }
        binding.topUnitSymbol.text = topUnit?.symbolResId?.let { context.getString(it) }
        binding.bottomUnitSymbol.text = bottomUnit?.symbolResId?.let { context.getString(it) }
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
                RadioItem(index, unit.getNameWithSymbol(context), unit)
            })
            RadioGroupDialog(context as Activity, items, converter!!.units.indexOf(propertyToChange.get())) {
                val unit = it as Converter.Unit
                if (unit == otherProperty.get()) {
                    switch()
                } else if (unit != propertyToChange.get()) {
                    propertyToChange.set(unit)
                    updateBottomValue()
                }
                updateUnitLabelsAndSymbols()
            }
        }
    }
}
