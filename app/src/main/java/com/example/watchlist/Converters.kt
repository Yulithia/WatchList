package com.example.watchlist

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {

    @TypeConverter
    fun fromGenreIdsList(genreIds: List<Int>?): String{
        return genreIds?.joinToString { ", " } ?: ""
    }

    @TypeConverter
    fun toGenreIdsList(data:String): List<Int>{
        return if (data.isEmpty()) {
            emptyList()
        } else {
            data.split(",").map { it.toInt() }
        }
    }


    object GenreConverter {
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        private val jsonAdapter = moshi.adapter(List::class.java)

        @TypeConverter
        fun fromGenresList(genres: List<Genre>?): String? {
            return genres?.let {
                moshi.adapter(List::class.java).toJson(it)
            }
        }

        @TypeConverter
        fun toGenresList(genresJson: String?): List<Genre>? {
            return genresJson?.let {
                // Now the adapter knows it's expecting a List of Genre
                moshi.adapter(List::class.java).fromJson(it) as List<Genre>?
            }
        }
    }
}