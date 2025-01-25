package com.example.watchlist

data class Movie(
    val title: String,
    val releaseDate: String,
    val rating: Double
)

data class MovieResponse(
    val results: List<Movie>
)