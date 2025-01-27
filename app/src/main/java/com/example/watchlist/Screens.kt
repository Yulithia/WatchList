package com.example.watchlist

sealed class Screens(val route: String) {
    data object PopularMoviesScreen : Screens("popular_movies_screen")
    data object SearchMovieScreen : Screens("search_movie_screen")
    data object MyMoviesScreen : Screens("my_movie_screen")
    data object MovieDetails : Screens("movie_details_screen/{index}") {
        fun createRoute(index: Int): String = "popular_movies_screen/$index"
    }
}