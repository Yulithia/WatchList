package com.example.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MovieDetails(
    viewModel: MovieViewModel,
    movie: MovieEntity,
    onFavoriteClick: (MovieEntity) -> Unit,
    onWatchedClick: (MovieEntity) -> Unit,
    onWantToWatchClick: (MovieEntity) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 25.sp
                )

                Text(
                    text = "Release date: ${movie.releaseDate}",
                    fontSize = 15.sp
                )

                Text(
                    text = movie.genres.joinToString(", ") { it.name },
                    fontSize = 18.sp
                )

                Text(
                    text = "Rating: ${movie.rating}",
                    fontSize = 18.sp
                )

                Row()
                {
                    IconButton(onClick = {viewModel.toggleFavourite(movie) }) {
                        val isFavourite = viewModel.isFavourite(movie)
                        Icon(
                            imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavourite) "Remove from Favorites" else "Add to Favorites"
                        )
                    }

                    IconButton(onClick = { viewModel.toggleWatched(movie) }
                    ) {
                        val isWatched = viewModel.isWatched(movie)
                        Icon(
                            imageVector = if (isWatched) Icons.Default.Check else Icons.Default.Add,
                            contentDescription = if (isWatched) "Remove from Watched" else "Add to Watched"
                        )
                    }

                    IconButton(
                        onClick = { viewModel.toggleWantToWatch(movie) }
                    ) {
                        val isWantToWatch = viewModel.isWantWatch(movie)
                        Icon(
                            imageVector = if (isWantToWatch) Icons.Default.Star else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isWantToWatch) "Remove from Want to Watch" else "Add to Want to Watch"
                        )
                    }
                }

            }
            }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = movie.overview,
                fontSize = 20.sp
            )
        }
    }
}

