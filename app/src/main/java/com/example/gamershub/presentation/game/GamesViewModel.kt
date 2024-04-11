package com.example.gamershub.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.gamershub.data.pagination.GameSource
import com.example.gamershub.data.preferences.DeviceSharedPreferences
import com.example.gamershub.domain.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GameRepository,
    sharedPreferences: DeviceSharedPreferences
) : ViewModel() {


      private var currentGenreIds: String = sharedPreferences.getGenreIds()

    val gamePager = Pager(PagingConfig(40)) {
        GameSource(repository, currentGenreIds)
    }.flow.cachedIn(viewModelScope)


    fun loadGames(genreIds: String) {
        currentGenreIds = genreIds
    }
}