package com.example.watchlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object PopularMoviesScreen : BottomBar(Screens.PopularMoviesScreen.route, "Popular", Icons.Default.Star)
    data object SearchMovieScreen : BottomBar(Screens.SearchMovieScreen.route, "Search", Icons.Default.Search)
    data object MyMoviesScreen : BottomBar(Screens.MyMoviesScreen.route, "My Movies", Icons.Default.Favorite)
}