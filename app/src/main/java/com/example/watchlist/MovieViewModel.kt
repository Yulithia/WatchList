package com.example.watchlist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = mutableStateListOf<Movie>()
    val movies: List<Movie> = _movies

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    var _genres = mutableStateMapOf<Int, String>()

    fun fetchGenres()
    {
        viewModelScope.launch {
            try {
                val genreResponse = RetrofitInstance.api.getGenres(apiKey = BuildConfig.TMDB_API_KEY)
                _genres.clear()
                _genres.putAll(genreResponse.genres.associate { it.id to it.name })
                Log.d("MovieViewModel", "Genres fetched: $_genres")
            } catch (e: Exception) {
                Log.e("Genre Fetch Error", e.message ?: "Unknown error")
            }
        }

    }

    fun fetchPopularMovies()
    {
        viewModelScope.launch {
            try{
                Log.d("MovieViewModel", "Fetching movies...")
                val fetchedMovies = RetrofitInstance.api.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY)
                Log.d("MovieViewModel", "Fetched movies: ${fetchedMovies.results}")
                _movies.clear()
                _movies.addAll(fetchedMovies.results)
            } catch(e: Exception)
            {
                e.printStackTrace()
            } finally{
                _isLoading.value = false
            }
        }
    }

    fun getPopularMovies(viewModel: MovieViewModel)
    {
        viewModel.fetchPopularMovies()
    }
    
}