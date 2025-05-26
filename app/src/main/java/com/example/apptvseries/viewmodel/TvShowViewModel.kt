package com.example.apptvseries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptvseries.model.TvShow
import com.example.apptvseries.repository.TvShowRepository
import com.example.apptvseries.repository.TvShowRepositoryImpl
import com.example.apptvseries.ui.state.TvShowUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TvShowViewModel : ViewModel() {

    private val repository: TvShowRepository = TvShowRepositoryImpl()

    private val _uiState = MutableStateFlow<TvShowUiState>(TvShowUiState.Loading)
    val uiState: StateFlow<TvShowUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private val tvShows = mutableListOf<TvShow>()
    private var isLoadingMore = false
    private var hasMorePages = true

    init {
        loadTvShows(isRefresh = true)
    }

    fun loadTvShows(isRefresh: Boolean = false) {
        if (isLoadingMore && !isRefresh) return

        viewModelScope.launch {
            try {
                if (isRefresh) {
                    _uiState.value = TvShowUiState.Loading
                    currentPage = 1
                    tvShows.clear()
                    hasMorePages = true
                } else {
                    if (!hasMorePages) return@launch
                    _uiState.value = TvShowUiState.LoadingMore(tvShows.toList())
                    isLoadingMore = true
                    currentPage++
                }

                repository.getMostPopularTvShows(currentPage)
                    .onSuccess { newShows ->
                        tvShows.addAll(newShows)
                        hasMorePages = newShows.isNotEmpty()
                        _uiState.value = TvShowUiState.Success(tvShows.toList())
                    }
                    .onFailure { error ->
                        val errorMessage = when {
                            error.message?.contains("Unable to resolve host") == true ->
                                "Erreur de connexion. Vérifiez votre connexion internet."
                            error.message?.contains("timeout") == true ->
                                "Délai d'attente dépassé. Réessayez."
                            else -> "Erreur lors du chargement des séries: ${error.message}"
                        }

                        if (isRefresh || tvShows.isEmpty()) {
                            _uiState.value = TvShowUiState.Error(errorMessage)
                        } else {
                            _uiState.value = TvShowUiState.Success(tvShows.toList())
                        }
                    }

            } finally {
                isLoadingMore = false
            }
        }
    }

    fun loadMoreTvShows() {
        loadTvShows(isRefresh = false)
    }

    fun retryLoading() {
        loadTvShows(isRefresh = true)
    }
}