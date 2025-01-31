package com.example.watchlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json


@Entity(tableName = "movies")
data class MovieEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    @Json(name  = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "vote_average")
    val rating: Double,
    @TypeConverters(GenreConverter::class)
    val genres: List<Genre>,
    val runtime: Int?,
    var isFavourite: Boolean = false,
    var isWatched: Boolean = false,
    var isWantToWatch: Boolean = false
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as MovieEntity
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}