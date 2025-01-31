package com.example.watchlist

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class GenreConverter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val genreListType = Types.newParameterizedType(List::class.java, Genre::class.java)
    private val genreAdapter: JsonAdapter<List<Genre>> = moshi.adapter(genreListType)

 //   @TypeConverter
   // fun fromGenresList(genres: List<Genre>?): String? {
     //   return genres?.let {
       //     moshi.adapter(List::class.java).toJson(it)
        //}
    //}

//    @TypeConverter
  //  fun toGenresList(genresJson: String?): List<Genre>? {
    //    return genresJson?.let {
      //      // Now the adapter knows it's expecting a List of Genre
        //    moshi.adapter(List::class.java).fromJson(it) as List<Genre>?
        //}
    //}

    @TypeConverter
    fun fromGenreList(value: List<Genre>?): String? {
        return genreAdapter.toJson(value)
    }

    // Convert a JSON string back to List<Genre>
    @TypeConverter
    fun toGenreList(value: String?): List<Genre>? {
        return if (value == null) {
            null
        } else {
            genreAdapter.fromJson(value)
        }
    }
}