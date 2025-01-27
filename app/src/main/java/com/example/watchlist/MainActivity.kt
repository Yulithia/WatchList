package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


class MainActivity : ComponentActivity() {
    private val viewModel = MovieViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchGenres()

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                Navigation(viewModel)
            }
        }

    }
}

//@Composable
//fun MainActivityContent(viewModel: MovieViewModel) {
  //  PopularMoviesScreen(movies, isLoading)
//}


//        RetrofitInstance.api.getPopularMovies(apiKey).enqueue(object : Callback<MovieResponse>{
//          override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//            if(response.isSuccessful){
//              val movies = response.body()?.results
//            Log.d("MainActivity", "Movies: $movies")
//      }
//    else{
//      Log.e("MainActivity", "Error: ${response.code()}")
//}
//}

//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//              Log.e("MainActivity", "Failure ${t.message}")
//        }
//  }
//)