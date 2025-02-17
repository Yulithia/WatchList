package com.example.watchlist

sealed class Screens(val route: String) {
    data object PopularMoviesScreen : Screens("popular_movies_screen")
    data object SearchMovieScreen : Screens("search_movie_screen")
    data object MyMoviesScreen : Screens("my_movie_screen")
    data object FavouriteMoviesScreen : Screens("favourite_movies")
    data object WatchedMoviesScreen : Screens("watched_movies")
    data object WantToWatchMoviesScreen : Screens("want_to_watch_movies")
    data object MovieDetails : Screens("movie_details_screen/{movieId}") {
        fun createRoute(movieId: Int): String = "movie_details_screen/$movieId"
    }
}