package com.example.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun MovieItem(movie: Movie, viewModel: MovieViewModel, onClick: () -> Unit) {
    val genreText = movie.genreIds.mapNotNull { genreId ->
        viewModel.genres.firstOrNull { it.id == genreId }?.name
    }.joinToString(", ")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ){
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = "Movie Poster",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
            .padding(8.dp)
        ){
            Text(
                text = movie.title,
                //fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Release date: " + movie.releaseDate,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text =  genreText,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}