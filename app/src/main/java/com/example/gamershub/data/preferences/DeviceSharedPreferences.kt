package com.example.gamershub.data.preferences

interface DeviceSharedPreferences {
    fun setIsSignedInAsGuest(signedIn: Boolean)
    fun isSignedInAsGuest(): Boolean

    fun setGenreIds(value: String)
    fun getGenreIds(): String

}