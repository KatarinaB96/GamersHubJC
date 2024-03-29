package com.example.gamershub.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamershub.domain.repository.GameRepository
import com.example.gamershub.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    var gameState by mutableStateOf(GameState())
        private set

    fun loadGames(gameId: Int) {
        viewModelScope.launch {
            gameState = gameState.copy(isLoading = true)

            gameState = when (val result = repository.getGames(gameId)) {
                is Resource.Success -> {
                    gameState.copy(game = result.data, isLoading = false, error = null)
                }

                is Resource.Error -> {
                    gameState.copy(game = null, isLoading = false, error = result.message)
                }
            }
        }
    }
}