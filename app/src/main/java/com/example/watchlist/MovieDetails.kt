package com.example.watchlist

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MovieDetails(movie: Movie, isFavorite: Boolean, onFavoriteClick: (Movie) -> Unit, genreText: String)
{
    Column(
        modifier = Modifier.fillMaxSize()
    )
    {


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

            // Spacer(modifier = Modifier.width(5.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                  .padding(5.dp)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 25.sp,
                    //style = MaterialTheme.typography.titleLarge,
                    // overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Release date: " + movie.releaseDate,
                    //style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp
                )

                Text(
                    text = genreText,
                    //style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp
                )

               // Text(
              //      text = "Runtime: " + movie.runtime.toString(),
                    //style = MaterialTheme.typography.bodyMedium,
              //      fontSize = 18.sp
             //   )

                Text(
                    text = "Rating: " + movie.rating.toString(),
                    //style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp
                )

                IconButton(onClick = { onFavoriteClick(movie) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
                    )
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