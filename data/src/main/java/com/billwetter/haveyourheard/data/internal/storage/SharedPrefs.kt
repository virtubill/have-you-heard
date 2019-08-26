package com.billwetter.haveyourheard.data.internal.storage

import android.content.SharedPreferences

internal class SharedPrefs(private val prefs: SharedPreferences) {
    var country: String?
        get() { return prefs.getString("country", null) }
        set(value) { savePref("country", value) }

    var language: String?
        get() { return prefs.getString("language", null) }
        set(value) { savePref("language", value) }

    private fun savePref(name: String, value: String?) {
        prefs.edit().putString(name, value).apply()
    }
}