package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.helpers.*
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.simplemobiletools.commons.helpers.LICENSE_ESPRESSO
import com.simplemobiletools.commons.helpers.LICENSE_KOTLIN
import com.simplemobiletools.commons.helpers.LICENSE_ROBOLECTRIC
import kotlinx.android.synthetic.main.activity_main.*
import me.grantland.widget.AutofitHelper
import android.widget.Toast
import com.simplemobiletools.calculator.helpers.CONSTANT.COSINE
import com.simplemobiletools.calculator.helpers.CONSTANT.DIGIT
import com.simplemobiletools.calculator.helpers.CONSTANT.DIVIDE
import com.simplemobiletools.calculator.helpers.CONSTANT.LEFT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_ONE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_THREE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_TWO
import com.simplemobiletools.calculator.helpers.CONSTANT.MINUS
import com.simplemobiletools.calculator.helpers.CONSTANT.MODULO
import com.simplemobiletools.calculator.helpers.CONSTANT.MULTIPLY
import com.simplemobiletools.calculator.helpers.CONSTANT.NATURAL_LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.PI
import com.simplemobiletools.calculator.helpers.CONSTANT.PLUS
import com.simplemobiletools.calculator.helpers.CONSTANT.POWER
import com.simplemobiletools.calculator.helpers.CONSTANT.RIGHT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.ROOT
import com.simplemobiletools.calculator.helpers.CONSTANT.SINE
import com.simplemobiletools.calculator.helpers.CONSTANT.TANGENT

class MainActivity : SimpleActivity(), Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false

    private lateinit var calc: CalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appLaunched()

        calc = CalculatorImpl(this, applicationContext)

        btn_plus.setOnClickListener { calc.handleOperation(PLUS); checkHaptic(it) }
        btn_minus.setOnClickListener { calc.handleOperation(MINUS); checkHaptic(it) }
        btn_multiply.setOnClickListener { calc.handleOperation(MULTIPLY); checkHaptic(it) }
        btn_divide.setOnClickListener { calc.handleOperation(DIVIDE); checkHaptic(it) }
        btn_modulo.setOnClickListener { calc.handleOperation(MODULO); checkHaptic(it) }
        btn_power.setOnClickListener { calc.handleOperation(POWER); checkHaptic(it) }
        btn_root.setOnClickListener { calc.handleOperation(ROOT); checkHaptic(it) }
        btn_left_bracket.setOnClickListener { calc.handleOperation(LEFT_BRACKET); checkHaptic(it) }
        btn_right_bracket.setOnClickListener { calc.handleOperation(RIGHT_BRACKET); checkHaptic(it) }
        btn_pi.setOnClickListener { calc.handleOperation(PI); checkHaptic(it) }
        btn_sin.setOnClickListener { calc.handleOperation(SINE); checkHaptic(it) }
        btn_cos.setOnClickListener { calc.handleOperation(COSINE); checkHaptic(it) }
        btn_tan.setOnClickListener { calc.handleOperation(TANGENT); checkHaptic(it) }
        btn_log.setOnClickListener { calc.handleOperation(LOGARITHM); checkHaptic(it) }
        btn_ln.setOnClickListener { calc.handleOperation(NATURAL_LOGARITHM); checkHaptic(it) }

        btn_del.setOnClickListener {calc.handleClear(formula.text.toString()); checkHaptic(it) }
        btn_all_clear.setOnClickListener { calc.handleReset()}

        btn_memory_1.setOnClickListener { calc.handleViewValue(MEMORY_ONE)}
        btn_memory_1.setOnLongClickListener{ calc.handleStore(result.text.toString(), MEMORY_ONE); true }

        btn_memory_2.setOnClickListener { calc.handleViewValue(MEMORY_TWO)}
        btn_memory_2.setOnLongClickListener{ calc.handleStore(result.text.toString(), MEMORY_TWO); true }

        btn_memory_3.setOnClickListener { calc.handleViewValue(MEMORY_THREE) }
        btn_memory_3.setOnLongClickListener{calc.handleStore(result.text.toString(), MEMORY_THREE); true }

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        btn_equals.setOnClickListener {

            try {
                calc.handleEquals(formula.text.toString()); checkHaptic(it)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }

        formula.setOnLongClickListener { copyToClipboard(false) }
        formula.setOnLongClickListener{ pasteFromClipBoard()}
        result.setOnLongClickListener { copyToClipboard(true) }

        AutofitHelper.create(result)
        AutofitHelper.create(formula)
        storeStateVariables()
        updateViewColors(calculator_holder, config.textColor)

        btn_shift.setOnClickListener {
            if(btn_shift.getCurrentTextColor()==resources.getColor(R.color.noah_5)){

                btn_shift.setTextColor(resources.getColor(R.color.noah_4))
                btn_shift.setBackgroundColor(resources.getColor(R.color.noah_5))

                btn_memory_1.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_memory_1.setTextColor(Color.WHITE)
                btn_memory_2.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_memory_2.setTextColor(Color.WHITE)
                btn_memory_3.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_memory_3.setTextColor(Color.WHITE)
                btn_pi.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_pi.setTextColor(Color.WHITE)
                btn_sin.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_sin.setTextColor(Color.WHITE)
                btn_cos.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_cos.setTextColor(Color.WHITE)
                btn_tan.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_tan.setTextColor(Color.WHITE)
                btn_reciprocal.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_reciprocal.setTextColor(Color.WHITE)
                btn_log.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_log.setTextColor(Color.WHITE)
                btn_root.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_root.setTextColor(Color.WHITE)
                btn_modulo.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_modulo.setTextColor(Color.WHITE)
                btn_power.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_power.setTextColor(Color.WHITE)
                btn_plus_minus.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_plus_minus.setTextColor(Color.WHITE)
                btn_ln.setBackgroundColor(resources.getColor(R.color.noah_4))
                btn_ln.setTextColor(Color.WHITE)

            }
            else {
                btn_shift.setTextColor(resources.getColor(R.color.noah_5))
                btn_shift.setBackgroundColor(resources.getColor(R.color.noah_4))

                btn_memory_1.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_memory_1.setTextColor(resources.getColor(R.color.noah_4))
                btn_memory_2.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_memory_2.setTextColor(resources.getColor(R.color.noah_4))
                btn_memory_3.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_memory_3.setTextColor(resources.getColor(R.color.noah_4))
                btn_pi.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_pi.setTextColor(resources.getColor(R.color.noah_4))
                btn_sin.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_sin.setTextColor(resources.getColor(R.color.noah_4))
                btn_cos.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_cos.setTextColor(resources.getColor(R.color.noah_4))
                btn_tan.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_tan.setTextColor(resources.getColor(R.color.noah_4))
                btn_reciprocal.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_reciprocal.setTextColor(resources.getColor(R.color.noah_4))
                btn_log.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_log.setTextColor(resources.getColor(R.color.noah_4))
                btn_root.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_root.setTextColor(resources.getColor(R.color.noah_4))
                btn_modulo.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_modulo.setTextColor(resources.getColor(R.color.noah_4))
                btn_power.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_power.setTextColor(resources.getColor(R.color.noah_4))
                btn_plus_minus.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_plus_minus.setTextColor(resources.getColor(R.color.noah_4))
                btn_ln.setBackgroundColor(resources.getColor(R.color.noah_5))
                btn_ln.setTextColor(resources.getColor(R.color.noah_4))
            }

        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
        if (storedUseEnglish != config.useEnglish) {
            restartActivity()
            return
        }

        if (storedTextColor != config.textColor) {
            updateViewColors(calculator_holder, config.textColor)
        }
        vibrateOnButtonPress = config.vibrateOnButtonPress
    }

    override fun onPause() {
        super.onPause()
        storeStateVariables()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> launchSettings()
            R.id.about -> launchAbout()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun storeStateVariables() {
        config.apply {
            storedTextColor = textColor
            storedUseEnglish = useEnglish
        }
    }

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun launchSettings() {
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        startAboutActivity(R.string.app_name, LICENSE_KOTLIN or LICENSE_AUTOFITTEXTVIEW or LICENSE_ROBOLECTRIC or LICENSE_ESPRESSO, BuildConfig.VERSION_NAME)
    }

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = formula.value
        if (copyResult) {
            value = result.value
        }

        return if (value.isEmpty()) {
            false
        } else {
            copyToClipboard(value)
            true
        }
    }

    private fun pasteFromClipBoard(): Boolean {
        //check clipboard
        var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.primaryClip.getItemAt(0).coerceToText(this).toString().isNum()){
            setFormula(clipboard.primaryClip.getItemAt(0).coerceToText(this).toString(), this)
            Toast.makeText(applicationContext,"Pasted from clipboard", 3).show()
            return true
        }
        else {
            //do nothing

            return false
        }
    }

    fun String.isNum() = matches(Regex("(([0-9]+)|(,))+"))

    override fun setValue(value: String, context: Context) {
        result.text = value
    }

    // used only by Robolectric
    override fun setValueDouble(d: Double) {
        calc.setValue(Formatter.doubleToString(d))
        calc.lastKey = DIGIT
    }

    override fun setFormula(value: String, context: Context) {
        if(value == ""){
            formula.text = ""
        }
        else{
            formula.text = formula.text.toString() + value
        }
    }
}