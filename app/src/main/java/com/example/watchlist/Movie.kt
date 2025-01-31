package com.example.watchlist
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
)

//fun Movie.toMovieEntity(viewModel: MovieViewModel): MovieEntity {
  //  val genreObjects = genreIds.map { id -> Genre(id, viewModel.genres[id][id] ?: "Unknown") }
    //return MovieEntity(
      //  id = this.id,
        //title = this.title,
//        overview = this.overview,
  //      posterPath = this.posterPath,
    //    releaseDate = this.releaseDate,
      //  rating = -1.0,
        //genres = genreObjects,
//        runtime = 0
  //  )
//}

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
) {
    operator fun get(id: Int): String? {
        return if (this.id == id) {
            this.name
        } else {
            null
        }
    }
}

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val results: List<Movie>
)
