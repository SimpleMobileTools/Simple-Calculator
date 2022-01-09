package com.simplemobiletools.calculator.dialogs

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.activities.SimpleActivity
import com.simplemobiletools.calculator.adapters.HistoryAdapter
import com.simplemobiletools.calculator.extensions.calculatorDB
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.models.History
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.ensureBackgroundThread
import kotlinx.android.synthetic.main.dialog_history.view.*

class HistoryDialog() {
    constructor(activity: SimpleActivity, items: List<History>, calc: CalculatorImpl) : this() {
        val mInflater = LayoutInflater.from(activity)
        val view = mInflater.inflate(R.layout.dialog_history, null)
        view.history_list.adapter = HistoryAdapter(activity, items, calc)

        AlertDialog.Builder(activity)
            .setPositiveButton(R.string.ok, null)
            .setNeutralButton(R.string.clear_history) { _, _ ->
                ensureBackgroundThread {
                    activity.applicationContext.calculatorDB.deleteHistory()
                    activity.toast(R.string.history_cleared)
                }
            }
            .create()
            .apply {
                activity.setupDialogStuff(view, this, R.string.history)
                (view.history_list.adapter as HistoryAdapter).setDialog(this)
            }
    }
}
