package com.example.watchlist

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDAO = MovieDatabase.getDatabase(application).movieDao()

    private val _selectedMovieDetails = MutableStateFlow<MovieEntity?>(null)
    val selectedMovieDetails: StateFlow<MovieEntity?> get() = _selectedMovieDetails

    private val _popularMovies = mutableStateListOf<Movie>()
    val popularMovies: List<Movie> = _popularMovies

    private val _searchResults = mutableStateListOf<Movie>()
    val searchResults: List<Movie> get() = _searchResults
    private val _isSearching = mutableStateOf(false)
    val isSearching: Boolean get() = _isSearching.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    private val _favourites = MutableStateFlow<List<MovieEntity>>(emptyList())
    val favourites: StateFlow<List<MovieEntity>> get() = _favourites

    private val _watched = MutableStateFlow<List<MovieEntity>>(emptyList())
    val watched: StateFlow<List<MovieEntity>> get() = _watched

    private val _wantToWatch = MutableStateFlow<List<MovieEntity>>(emptyList())
    val wantToWatch: StateFlow<List<MovieEntity>> get() = _wantToWatch

    private var _genres = mutableStateListOf<Genre>()
    val genres: List<Genre> get() = _genres

    init {
        fetchGenres()
        fetchPopularMovies()

        viewModelScope.launch {
            movieDAO.getFavouriteMovies().collect { _favourites.value = it }
        }
        viewModelScope.launch {
            movieDAO.getWatchedMovies().collect { _watched.value = it }
        }
        viewModelScope.launch {
            movieDAO.getWantToWatchMovies().collect { _wantToWatch.value = it }
        }
    }

    private fun fetchGenres()
    {
        viewModelScope.launch {
            try {
                val genreResponse = RetrofitInstance.api.getGenres(apiKey = BuildConfig.TMDB_API_KEY)
                _genres.clear()
                _genres.addAll(genreResponse.genres)
                Log.d("MovieViewModel", "Genres fetched: $_genres")
            } catch (e: Exception) {
                Log.e("Genre Fetch Error", e.message ?: "Unknown error")
            }
        }

    }

    private fun fetchPopularMovies()
    {
        viewModelScope.launch {
            try{
                Log.d("MovieViewModel", "Fetching movies...")
                val fetchedMovies = RetrofitInstance.api.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY)
                Log.d("MovieViewModel", "Fetched movies: ${fetchedMovies.results}")

                _popularMovies.clear()
                _popularMovies.addAll(fetchedMovies.results)
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

    suspend fun findMovieById(id: Int): MovieEntity {
        return movieDAO.getMovieById(id) ?: RetrofitInstance.api.getMovieDetails(id, BuildConfig.TMDB_API_KEY)

    }

    fun getGenreTextByIds(genreIds: List<Int>): String {
        return genreIds.mapNotNull { genreId ->
            _genres.find { it.id == genreId }?.name
        }.joinToString(", ")
    }

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val cachedMovie = movieDAO.getMovieById(movieId)
            if (cachedMovie != null) {
                _selectedMovieDetails.value = cachedMovie
            } else {
                try {
                    val movieDetails = RetrofitInstance.api.getMovieDetails(
                        movieId = movieId,
                        apiKey = BuildConfig.TMDB_API_KEY
                    )
                    Log.d("MovieViewModel", "Movie Details: $movieDetails")
                    val movieEntity = MovieEntity(
                        id = movieDetails.id,
                        title = movieDetails.title,
                        overview = movieDetails.overview,
                        posterPath = movieDetails.posterPath,
                        releaseDate = movieDetails.releaseDate,
                        rating = movieDetails.rating,
                        genres = movieDetails.genres,
                        runtime = movieDetails.runtime
                    )
                    movieDAO.insertMovie(movieEntity)
                    _selectedMovieDetails.value = movieEntity
                } catch (e: Exception) {
                    Log.e("MovieViewModel", "Error fetching movie details: ${e.message}")
                    e.printStackTrace()
                }
            }
        }
    }

    fun toggleFavourite(movie: MovieEntity?)
    {
        viewModelScope.launch {
            val existingMovie =
                movie?.let { movieDAO.getMovieById(it.id) }

            if (existingMovie != null) {
                val updatedMovie = existingMovie.copy(isFavourite = !existingMovie.isFavourite)
                movieDAO.updateMovie(updatedMovie)

                val updatedList = _favourites.value.toMutableList()
                updatedList.removeIf { it.id == movie.id }
                updatedList.add(updatedMovie)
                _favourites.value = updatedList
            } else {
                val newMovie = movie?.copy(isFavourite = true)
                if (newMovie != null) {
                    movieDAO.insertMovie(newMovie)
                }

                val updatedList = _favourites.value.toMutableList()
                if (newMovie != null) {
                    updatedList.add(newMovie)
                }
                _favourites.value = updatedList
            }
        }
    }

    fun isFavourite(movie: MovieEntity?): Boolean {
        return movie?.let {
            _favourites.value.any { movie.id == it.id }
        } ?: false
    }

    fun toggleWatched(movie: MovieEntity?)
    {
        viewModelScope.launch {
            val existingMovie =
                movie?.let { movieDAO.getMovieById(it.id) }

            if (existingMovie != null) {
                val updatedMovie = existingMovie.copy(isWatched = !existingMovie.isWatched)
                movieDAO.updateMovie(updatedMovie)

                val updatedList = _watched.value.toMutableList()
                updatedList.removeIf { it.id == movie.id }
                updatedList.add(updatedMovie)
                _watched.value = updatedList
            } else {
                val newMovie = movie?.copy(isWatched = true)
                if (newMovie != null) {
                    movieDAO.insertMovie(newMovie)
                }

                val updatedList = _watched.value.toMutableList()
                if (newMovie != null) {
                    updatedList.add(newMovie)
                }
                _watched.value = updatedList
            }
        }
    }

    fun isWatched(movie: MovieEntity?): Boolean {
        return movie?.let {
            _watched.value.any { movie.id == it.id }
        } ?: false
    }

    fun toggleWantToWatch(movie: MovieEntity?)
    {
        viewModelScope.launch {
            val existingMovie =
                movie?.let { movieDAO.getMovieById(it.id) }

            if (existingMovie != null) {
                val updatedMovie = existingMovie.copy(isWantToWatch = !existingMovie.isWantToWatch)
                movieDAO.updateMovie(updatedMovie)

                val updatedList = _wantToWatch.value.toMutableList()
                updatedList.removeIf { it.id == movie.id }
                updatedList.add(updatedMovie)
                _wantToWatch.value = updatedList
            } else {
                val newMovie = movie?.copy(isWantToWatch = true)
                if (newMovie != null) {
                    movieDAO.insertMovie(newMovie)
                }

                val updatedList = _wantToWatch.value.toMutableList()
                if (newMovie != null) {
                    updatedList.add(newMovie)
                }
                _wantToWatch.value = updatedList
            }
        }
    }

    fun isWantWatch(movie: MovieEntity?): Boolean {
        return movie?.let {
            _wantToWatch.value.any { movie.id == it.id }
        } ?: false
    }

    fun getFavouriteMovies(): Flow<List<MovieEntity>> = movieDAO.getFavouriteMovies()
    fun getWatchedMovies(): Flow<List<MovieEntity>> = movieDAO.getWatchedMovies()
    fun getWantToWatchMovies(): Flow<List<MovieEntity>> = movieDAO.getWantToWatchMovies()

}