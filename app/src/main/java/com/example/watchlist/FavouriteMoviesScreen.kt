package com.example.watchlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavouriteMoviesScreen(viewModel: MovieViewModel, onMovieClick: (Movie) -> Unit) {
    val favourites = viewModel.favorites
    val genres by remember { derivedStateOf { viewModel._genres } }

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
                        val genreText = movie.genreIds.mapNotNull { genres[it] }.joinToString(", ")
                        MovieItem(movie, genreText) { onMovieClick(movie) }
                    }
                }
            }
        }
    }
}