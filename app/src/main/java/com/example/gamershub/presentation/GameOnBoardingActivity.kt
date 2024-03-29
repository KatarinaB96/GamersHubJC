package com.example.gamershub.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.gamershub.data.preferences.DeviceSharedPreferences
import com.example.gamershub.navigation.SetupNavGraph
import com.example.gamershub.presentation.ui.theme.GamersHubTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameOnBoardingActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: DeviceSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            GamersHubTheme {
                val navController = rememberNavController()
                SetupNavGraph(this, sharedPreferences, navController)
            }
        }
    }
}



