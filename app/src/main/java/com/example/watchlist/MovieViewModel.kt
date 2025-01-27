package com.example.watchlist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = mutableStateListOf<Movie>()
    val movies: List<Movie> = _movies

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value


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
    
}