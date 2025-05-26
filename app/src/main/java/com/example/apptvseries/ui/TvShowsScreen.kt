package com.example.apptvseries.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.apptvseries.model.TvShow
import com.example.apptvseries.viewmodel.TvShowViewModel
import com.example.apptvseries.ui.state.TvShowUiState

@Composable
fun TvShowsScreen(
    viewModel: TvShowViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    // Détecter quand on arrive vers la fin de la liste pour charger plus
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            val currentShows = when (val state = uiState) {
                is TvShowUiState.Success -> state.shows
                is TvShowUiState.LoadingMore -> state.currentShows
                else -> emptyList()
            }
            lastVisibleItem != null && lastVisibleItem.index >= currentShows.size - 4
        }
    }

    // Charger plus de données quand on arrive en bas
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && uiState is TvShowUiState.Success) {
            viewModel.loadMoreTvShows()
        }
    }

    when (val currentState = uiState) {
        is TvShowUiState.Loading -> {
            LoadingScreen()
        }

        is TvShowUiState.Error -> {
            ErrorScreen(
                message = currentState.message,
                onRetry = { viewModel.retryLoading() }
            )
        }

        is TvShowUiState.Success -> {
            TvShowGrid(
                shows = currentState.shows,
                gridState = gridState
            )
        }

        is TvShowUiState.LoadingMore -> {
            TvShowGridWithLoadingMore(
                shows = currentState.currentShows,
                gridState = gridState
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text(
                text = "Chargement des séries...",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "😔",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(onClick = onRetry) {
                Text("Réessayer")
            }
        }
    }
}

@Composable
private fun TvShowGrid(
    shows: List<TvShow>,
    gridState: LazyGridState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        state = gridState
    ) {
        items(shows) { show ->
            TvShowCard(show = show)
        }
    }
}

@Composable
private fun TvShowGridWithLoadingMore(
    shows: List<TvShow>,
    gridState: LazyGridState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        state = gridState
    ) {
        items(shows) { show ->
            TvShowCard(show = show)
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun TvShowCard(show: TvShow) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Column {
            AsyncImage(
                model = show.imageThumbnailPath,
                contentDescription = show.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Text(
                text = show.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}