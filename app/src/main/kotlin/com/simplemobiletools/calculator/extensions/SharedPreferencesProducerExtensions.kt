package com.simplemobiletools.calculator.extensions

import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

context (SharedPreferences)
fun <T> sharedPreferencesCallback(
    value: () -> T?,
) = callbackFlow {
    val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
            trySend(value())
        }

    registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    awaitClose { unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener) }
}
