package com.example.watchlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("SELECT * FROM movies WHERE isFavourite = 1")
    fun getFavouriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isWatched = 1")
    fun getWatchedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isWantToWatch = 1")
    fun getWantToWatchMovies(): Flow<List<MovieEntity>>

    @Update
    suspend fun updateMovie(movie: MovieEntity)

}