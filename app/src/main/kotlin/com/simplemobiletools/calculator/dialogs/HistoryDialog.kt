package com.simplemobiletools.calculator.dialogs

import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.activities.SimpleActivity
import com.simplemobiletools.calculator.adapters.HistoryAdapter
import com.simplemobiletools.calculator.extensions.calculatorDB
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.models.History
import com.simplemobiletools.commons.extensions.setupDialogStuff
import com.simplemobiletools.commons.extensions.toast
import com.simplemobiletools.commons.helpers.ensureBackgroundThread
import kotlinx.android.synthetic.main.dialog_history.view.*

class HistoryDialog(activity: SimpleActivity, items: List<History>, calculator: CalculatorImpl) {
    var dialog: AlertDialog? = null

    init {
        val view = activity.layoutInflater.inflate(R.layout.dialog_history, null)

        AlertDialog.Builder(activity)
            .setPositiveButton(R.string.ok, null)
            .setNeutralButton(R.string.clear_history) { _, _ ->
                ensureBackgroundThread {
                    activity.applicationContext.calculatorDB.deleteHistory()
                    activity.toast(R.string.history_cleared)
                }
            }.apply {
                activity.setupDialogStuff(view, this, R.string.history) { alertDialog ->
                    dialog = alertDialog
                }
            }

        view.history_list.adapter = HistoryAdapter(activity, items, calculator) {
            dialog?.dismiss()
        }
    }
}
