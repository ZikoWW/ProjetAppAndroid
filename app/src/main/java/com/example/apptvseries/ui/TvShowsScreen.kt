package com.example.apptvseries.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.apptvseries.model.TvShow
import com.example.apptvseries.viewmodel.TvShowViewModel
import com.example.apptvseries.ui.state.TvShowUiState
import com.example.apptvseries.ui.components.AppLogo
import com.example.apptvseries.ui.components.LogoSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowsScreenWithSearch(
    viewModel: TvShowViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()
    var searchQuery by remember { mutableStateOf("") }

    // Debounce pour la recherche automatique (attendre 500ms après la dernière frappe)
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank() && searchQuery.length >= 2) {
            kotlinx.coroutines.delay(800) // Attendre 800ms
            viewModel.searchTvShows(searchQuery)
        } else if (searchQuery.isEmpty()) {
            viewModel.clearSearch()
        }
    }

    // Détecter quand on arrive vers la fin de la liste pour charger plus
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            val currentShows = when (val state = uiState) {
                is TvShowUiState.Success -> state.shows
                is TvShowUiState.LoadingMore -> state.currentShows
                is TvShowUiState.SearchResults -> emptyList() // Pas de pagination pour la recherche
                else -> emptyList()
            }
            lastVisibleItem != null && lastVisibleItem.index >= currentShows.size - 4
        }
    }

    // Charger plus de données quand on arrive en bas (seulement pour les séries populaires)
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && uiState is TvShowUiState.Success) {
            viewModel.loadMoreTvShows()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header avec logo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLogo(size = LogoSize.Medium)
            Text(
                text = "TV Series",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Barre de recherche
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Rechercher") },
                leadingIcon = {
                    if (uiState is TvShowUiState.Loading && searchQuery.isNotBlank()) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Rechercher",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                searchQuery = ""
                                viewModel.clearSearch()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Effacer",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // La recherche se lance automatiquement via LaunchedEffect
                        // Ici on ne fait que fermer le clavier
                    }
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        // Titre de section
        when (val currentState = uiState) {
            is TvShowUiState.SearchResults -> {
                Text(
                    text = "Résultats pour \"${currentState.query}\" (${currentState.shows.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            is TvShowUiState.Success -> {
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "Séries populaires",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            else -> { /* Pas de titre pour les autres états */ }
        }

        // Contenu principal
        when (val currentState = uiState) {
            is TvShowUiState.Loading -> {
                LoadingScreen()
            }

            is TvShowUiState.Error -> {
                ErrorScreen(
                    message = currentState.message,
                    onRetry = {
                        if (searchQuery.isNotBlank()) {
                            viewModel.searchTvShows(searchQuery)
                        } else {
                            viewModel.retryLoading()
                        }
                    }
                )
            }

            is TvShowUiState.Success -> {
                if (currentState.shows.isEmpty()) {
                    EmptyStateScreen(message = "Aucune série trouvée")
                } else {
                    TvShowGrid(
                        shows = currentState.shows,
                        gridState = gridState
                    )
                }
            }

            is TvShowUiState.LoadingMore -> {
                TvShowGridWithLoadingMore(
                    shows = currentState.currentShows,
                    gridState = gridState
                )
            }

            is TvShowUiState.SearchResults -> {
                if (currentState.shows.isEmpty()) {
                    EmptyStateScreen(
                        message = "Aucun résultat pour \"${currentState.query}\"",
                        showRetryButton = false
                    )
                } else {
                    TvShowGrid(
                        shows = currentState.shows,
                        gridState = gridState
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text(
                text = "Chargement des séries...",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Réessayer")
            }
        }
    }
}

@Composable
private fun EmptyStateScreen(
    message: String,
    showRetryButton: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🔍",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
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
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
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
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
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
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun TvShowCard(show: TvShow) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            AsyncImage(
                model = show.imageThumbnailPath,
                contentDescription = show.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Text(
                text = show.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}