package com.example.gamershub.presentation.sign_in

import android.content.Intent
import android.content.IntentSender

interface GoogleAuthUiClient {
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): SignInResult
    suspend fun signOut()
    fun getSignedInUser(): UserData?
}