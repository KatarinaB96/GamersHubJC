package com.example.gamershub.presentation.sign_in

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val singInErrorMessage: String? = null
)
