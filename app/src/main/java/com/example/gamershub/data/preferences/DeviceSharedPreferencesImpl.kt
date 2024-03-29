package com.example.gamershub.data.preferences

import android.content.Context
import android.content.SharedPreferences

class DeviceSharedPreferencesImpl(context: Context) : DeviceSharedPreferences {
    companion object {
        private const val SHARED_PREFS_FILE_NAME = "device_preferences"
        private const val SIGNED_IN_AS_GUEST_KEY = "signed_in_as_guest"
        private const val GENRE_IDS = "genre_ids"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)

    override fun setIsSignedInAsGuest(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(SIGNED_IN_AS_GUEST_KEY, value)
        editor.apply()
    }

    override fun isSignedInAsGuest(): Boolean {
        return sharedPreferences.getBoolean(SIGNED_IN_AS_GUEST_KEY, false)
    }

    override fun setGenreIds(value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(GENRE_IDS, value)
        editor.apply()
    }

    override fun getGenreIds(): String {
        return sharedPreferences.getString(GENRE_IDS, "") ?: ""
    }
}