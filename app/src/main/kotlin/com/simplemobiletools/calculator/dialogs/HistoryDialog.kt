package com.simplemobiletools.calculator.dialogs

import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.activities.SimpleActivity
import com.simplemobiletools.calculator.adapters.HistoryAdapter
import com.simplemobiletools.calculator.databinding.DialogHistoryBinding
import com.simplemobiletools.calculator.extensions.calculatorDB
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.models.History
import com.simplemobiletools.commons.extensions.getAlertDialogBuilder
import com.simplemobiletools.commons.extensions.setupDialogStuff
import com.simplemobiletools.commons.extensions.toast
import com.simplemobiletools.commons.helpers.ensureBackgroundThread

class HistoryDialog(activity: SimpleActivity, items: List<History>, calculator: CalculatorImpl) {
    private var dialog: AlertDialog? = null

    init {

        val view = DialogHistoryBinding.inflate(activity.layoutInflater, null, false)

        activity.getAlertDialogBuilder()
            .setPositiveButton(com.simplemobiletools.commons.R.string.ok, null)
            .setNeutralButton(R.string.clear_history) { _, _ ->
                ensureBackgroundThread {
                    activity.applicationContext.calculatorDB.deleteHistory()
                    activity.toast(R.string.history_cleared)
                }
            }.apply {
                activity.setupDialogStuff(view.root, this, R.string.history) { alertDialog ->
                    dialog = alertDialog
                }
            }

        view.historyList.adapter = HistoryAdapter(activity, items, calculator) {
            dialog?.dismiss()
        }
    }
}
