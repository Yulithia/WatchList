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
fun WantToWatchMoviesScreen(viewModel: MovieViewModel, onMovieClick: (MovieEntity?) -> Unit) {
    val wantToWatch by viewModel.wantToWatch.collectAsState()
    //val genres by remember { derivedStateOf { viewModel.genres } }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (wantToWatch.isEmpty()) {
                Text("No want to watch movies yet", Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(wantToWatch) { movie ->
                        //val genreText = movie?.genres?.mapNotNull { genres[it] }?.joinToString(", ")
                        MyMovieItem(movie, viewModel) { onMovieClick(movie) }
                    }
                }
            }
        }
    }
}