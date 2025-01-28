package com.example.watchlist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = mutableStateListOf<Movie>()
    val movies: List<Movie> = _movies

    private val _searchResults = mutableStateListOf<Movie>()
    val searchResults: List<Movie> get() = _searchResults
    private val _isSearching = mutableStateOf(false)
    val isSearching: Boolean get() = _isSearching.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    private val _favorites = mutableStateListOf<Movie>()
    val favorites: List<Movie> get() = _favorites

    var _genres = mutableStateMapOf<Int, String>()

    init {
        fetchGenres()
        fetchPopularMovies()
    }

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
             //   Log.d("MovieViewModel", "Fetching movies...")
                val fetchedMovies = RetrofitInstance.api.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY)
               // Log.d("MovieViewModel", "Fetched movies: ${fetchedMovies.results}")
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

    fun searchMovies(query: String)
    {
        viewModelScope.launch{
            _isSearching.value = true
            try{
                val response = RetrofitInstance.api.searchMovies(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    query = query
                )
                Log.d("SearchResponse", "Results: ${response.results}")
                _searchResults.clear()
                _searchResults.addAll(response.results)
            }catch (e: Exception)
            {
                Log.e("MovieViewModel", "Error searching for movies: ${e.message}")
            } finally {
                _isSearching.value = false
            }
        }
    }

    fun findMovieById(id: Int): Movie? {
        return movies.find { it.id == id } ?: searchResults.find { it.id == id }
    }

    fun toggleFavourite(movie: Movie)
    {
        if(_favorites.contains(movie))
        {
            _favorites.remove(movie)
        }else{
            _favorites.add(movie)
        }
    }

    fun isFavourite(movie: Movie): Boolean
    {
        return _favorites.contains(movie)
    }
}