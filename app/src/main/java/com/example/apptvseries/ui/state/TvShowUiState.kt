package com.example.apptvseries.ui.state

import com.example.apptvseries.model.TvShow

sealed class TvShowUiState {
    object Loading : TvShowUiState()
    data class LoadingMore(val currentShows: List<TvShow>) : TvShowUiState()
    data class Success(val shows: List<TvShow>) : TvShowUiState()
    data class SearchResults(val shows: List<TvShow>, val query: String) : TvShowUiState()
    data class Error(val message: String) : TvShowUiState()
}