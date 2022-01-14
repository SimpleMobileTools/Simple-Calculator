package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.simplemobiletools.calculator.extensions.calculatorDB
import com.simplemobiletools.calculator.models.History
import com.simplemobiletools.commons.helpers.ensureBackgroundThread

class HistoryHelper(val context: Context) {
    fun getHistory(callback: (calculations: ArrayList<History>) -> Unit) {
        ensureBackgroundThread {
            val notes = context.calculatorDB.getHistory() as ArrayList<History>

            Handler(Looper.getMainLooper()).post {
                callback(notes)
            }
        }
    }

    fun insertOrUpdateHistoryEntry(entry: History) {
        ensureBackgroundThread {
            context.calculatorDB.insertOrUpdate(entry)
        }
    }
}
