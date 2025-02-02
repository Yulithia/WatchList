package com.example.watchlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavouriteMoviesScreen(viewModel: MovieViewModel, onMovieClick: (MovieEntity?) -> Unit) {
    val favourites by viewModel.favourites.collectAsState()

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (favourites.isEmpty()) {
                Text("No favorites yet", Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(favourites) { movie ->
                        MyMovieItem(movie, viewModel) { onMovieClick(movie) }
                    }
                }
            }
        }
    }
}