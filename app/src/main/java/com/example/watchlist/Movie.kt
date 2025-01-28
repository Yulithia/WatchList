package com.example.watchlist
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val rating: Double,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    //@Json(name = "runtime") val runtime: Int
)

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val results: List<Movie>
)





