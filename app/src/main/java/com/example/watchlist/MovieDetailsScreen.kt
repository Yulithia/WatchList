package com.example.watchlist

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment


@Composable
fun MovieDetailsScreen(viewModel: MovieViewModel, movieId: Int, onClick: () -> Unit) {
    val movieDetails by viewModel.selectedMovieDetails.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    movieDetails?.let {
        MovieDetails(viewModel, movie = movieDetails!!,
            onFavoriteClick = { viewModel.toggleFavourite(it) },
            onWatchedClick = { viewModel.toggleWatched(it) },
            onWantToWatchClick = { viewModel.toggleWantToWatch(it) })
    } ?: run {
        Box(
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}