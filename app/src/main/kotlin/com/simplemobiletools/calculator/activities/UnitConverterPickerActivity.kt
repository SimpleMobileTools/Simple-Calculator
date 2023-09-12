package com.simplemobiletools.calculator.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.adapters.UnitTypesAdapter
import com.simplemobiletools.calculator.databinding.ActivityUnitConverterPickerBinding
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.commons.extensions.appLaunched
import com.simplemobiletools.commons.extensions.viewBinding
import com.simplemobiletools.commons.helpers.NavigationIcon
import com.simplemobiletools.commons.views.AutoGridLayoutManager

class UnitConverterPickerActivity : SimpleActivity() {
    private val binding by viewBinding(ActivityUnitConverterPickerBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        isMaterialActivity = true
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appLaunched(BuildConfig.APPLICATION_ID)
        updateMaterialActivityViews(binding.unitConverterPickerCoordinator, null, useTransparentNavigation = false, useTopSearchMenu = false)
        setupMaterialScrollListener(binding.unitTypesGrid, binding.unitConverterPickerToolbar)

        binding.unitTypesGrid.layoutManager = AutoGridLayoutManager(this, resources.getDimensionPixelSize(R.dimen.unit_type_size))
        binding.unitTypesGrid.adapter = UnitTypesAdapter(this, Converter.ALL) {
            Intent(this, UnitConverterActivity::class.java).apply {
                putExtra(UnitConverterActivity.EXTRA_CONVERTER_ID, it)
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        setupToolbar(binding.unitConverterPickerToolbar, NavigationIcon.Arrow)

        if (config.preventPhoneFromSleeping) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onPause() {
        super.onPause()
        if (config.preventPhoneFromSleeping) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}
