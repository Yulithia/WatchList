package com.example.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchMovieScreen(viewModel: MovieViewModel, onMovieClick: (Movie) -> Unit) {
    //val genres by remember { derivedStateOf { viewModel._genres } }
    val searchResults by remember { derivedStateOf { viewModel.searchResults }}
    val isSearching by remember { derivedStateOf { viewModel.isSearching }}
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.background(Color.Gray)
    ) {
        Text(
            text = "Search",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray)
        )

        TextField(
            value = query,
            onValueChange = {
                query = it
                if(it.isNotBlank()){
                    viewModel.searchMovies(it)
                }
            },
            placeholder = { Text("Search for a movie")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if(isSearching){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (searchResults.isEmpty() && query.isNotEmpty()) {
            Text("No results found", style = MaterialTheme.typography.bodyLarge)
        }else{
            LazyColumn {
                items(searchResults) { movie ->
                    //val genreText = movie?.genreIds?.mapNotNull { genres[it] }?.joinToString(", ")
                    MovieItem(movie, viewModel) { onMovieClick(movie) }

                }
        }
    }
}
}