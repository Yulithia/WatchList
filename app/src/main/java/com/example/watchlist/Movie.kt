package com.example.watchlist
import com.squareup.moshi.Json

//data class Movie(
  //  val id: Int,
    //val title: String,
    //val overview: String,
    //val posterPath: String,
    //val releaseDate: String,
    //val rating: Double,
    //val genreIds: List<Int>
//)


data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val rating: Double,
    //@Json(name = "genre_ids") val genreIds: List<Int>
)

data class MovieResponse(
    val results: List<Movie>
)