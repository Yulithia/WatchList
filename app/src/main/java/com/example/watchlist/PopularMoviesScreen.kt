package com.example.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PopularMoviesScreen(viewModel: MovieViewModel) {
    viewModel.fetchPopularMovies()
    val movies by remember { derivedStateOf { viewModel.movies } }
    val isLoading by remember { derivedStateOf { viewModel.isLoading } }
    val genres by remember { derivedStateOf { viewModel._genres } }

    Scaffold { padding ->
        Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
         //   Text(
           //     text = "Popular Movies",
             //   fontSize = 32.sp,
               // textAlign = TextAlign.Center,
            //)

            //Spacer(modifier = Modifier.height(4.dp))

            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                }

                movies.isEmpty() -> {
                    Text(
                        text = "No movies available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                genres.isEmpty() -> {
                    // Show a loading state while genres are being fetched
                    Text("Loading genres...", modifier = Modifier.fillMaxSize())
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()) {
                        items(movies) { movie ->
                            val genreText = movie.genreIDs.mapNotNull { genres[it] }.joinToString(", ")
                            MovieItem(movie = movie, viewModel, genreText = genreText)
                        }
                    }
                }
            }
        }
    }
}