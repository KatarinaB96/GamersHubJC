package com.example.gamershub.presentation.genre

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
class GenresViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    var genreState by mutableStateOf(GenreState())
    fun loadGenres() {
        viewModelScope.launch {
            genreState = genreState.copy(
                isLoading = true,
                error = null
            )

            when (val result = repository.getGenres()) {
                is Resource.Success -> {
                    genreState = genreState.copy(
                        genre = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    genreState = genreState.copy(
                        genre = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}