package com.billwetter.haveyourheard.data.internal

import android.content.SharedPreferences

class SharedPrefs(private val prefs: SharedPreferences) {

    var country: String?
    get() { return prefs.getString("country", null) }
    set(value) { savePref("country", value) }

    private fun savePref(name: String, value: String?) {
        prefs.edit().putString(name, value).apply()
    }
}