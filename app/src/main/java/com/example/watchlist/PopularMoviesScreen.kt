package com.example.watchlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember



@Composable
fun PopularMoviesScreen(viewModel: MovieViewModel, onMovieClick: (Movie) -> Unit) {
    val movies by remember { derivedStateOf { viewModel.popularMovies } }
    val isLoading by remember { derivedStateOf { viewModel.isLoading } }

    Scaffold { padding ->
        Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
         //   Text(
           //     text = "Popular Movies",
             //   fontSize = 32.sp,
               // textAlign = TextAlign.Center,
            //)


            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                }

                !isLoading && movies.isEmpty() -> {
                    Text(
                        text = "No movies available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()) {
                        items(movies) { movie ->
                            MovieItem(movie, viewModel) { onMovieClick(movie) }
                        }
                    }
                }
            }
        }
    }
}