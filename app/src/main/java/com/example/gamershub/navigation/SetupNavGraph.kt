package com.example.gamershub.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamershub.data.preferences.DeviceSharedPreferences
import com.example.gamershub.presentation.game.GameDetailsScreen
import com.example.gamershub.presentation.game.GameDetailsViewModel
import com.example.gamershub.presentation.game.GamesScreen
import com.example.gamershub.presentation.game.GamesViewModel
import com.example.gamershub.presentation.genre.GenreScreen
import com.example.gamershub.presentation.genre.GenresViewModel
import com.example.gamershub.presentation.settings.SettingsScreen
import com.example.gamershub.presentation.sign_in.GoogleAuthUiClientImpl
import com.example.gamershub.presentation.sign_in.SignInScreen
import com.example.gamershub.presentation.sign_in.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(
    lifecycleOwner: LifecycleOwner,
    sharedPreferences: DeviceSharedPreferences
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val googleAuthUiClient by lazy {
        GoogleAuthUiClientImpl(
            context = context, oneTapClient = Identity.getSignInClient(context)
        )
    }
    val genresViewModel = hiltViewModel<GenresViewModel>()
    val gamesViewModel = hiltViewModel<GamesViewModel>()
    val genreState = genresViewModel.genreState

    val gameDetailsViewModel = hiltViewModel<GameDetailsViewModel>()
    val gameState = gameDetailsViewModel.gameState

    val lifecycleScope = lifecycleOwner.lifecycleScope
    NavHost(
        navController = navController,
        startDestination = Screen.SignInScreen.route
    ) {
        composable(route = Screen.SignInScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null || sharedPreferences.isSignedInAsGuest()) {
                    gamesViewModel.loadGames(sharedPreferences.getGenreIds())
                    navController.navigate("GamesScreen")
                }
            }

            val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == ComponentActivity.RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(result.data ?: return@launch)
                            viewModel.onSignResult(signInResult)
                        }
                    }
                })

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    navController.navigate("GenreScreen")
                    viewModel.resetState()
                }
            }
            SignInScreen(state = state, onSignInWithGoogleClick = {
                lifecycleScope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                    genresViewModel.loadGenres()
                }
            }, onSignInClick = {
                sharedPreferences.setIsSignedInAsGuest(true)
                genresViewModel.loadGenres()
                navController.popBackStack()
                navController.navigate("GenreScreen")
            })
        }
        composable(route = Screen.GenreScreen.route) {
            GenreScreen(
                genreState,
                onGenresClick = {
                    gamesViewModel.loadGames(sharedPreferences.getGenreIds())
                    navController.popBackStack()
                    navController.navigate("GamesScreen")
                }, sharedPreferences
            )
        }

        composable(route = Screen.GamesScreen.route) {
            GamesScreen(
                onGameClick = { game ->
                    gameDetailsViewModel.loadGames(game.id)
                    navController.navigate("GameDetailsScreen")
                }) {
                navController.navigate("SettingsScreen")
            }
        }

        composable(route = Screen.GameDetailsScreen.route) {
            GameDetailsScreen(gameState)
        }

        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        navController.popBackStack()
                        navController.navigate("SignInScreen")
                    }
                }, onReturnHome = {
                    sharedPreferences.setIsSignedInAsGuest(false)
                    navController.popBackStack("GamesScreen", true)
                    navController.navigate("SignInScreen")
                },
                onChangeGenres = {
                    genresViewModel.loadGenres()
                    navController.navigate("GenreScreen")
                })
        }
    }
}
