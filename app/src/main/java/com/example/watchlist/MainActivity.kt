package com.example.watchlist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {
    private val viewModel = MovieViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainActivityContent(viewModel)
        }

    }
}

@Composable
fun MainActivityContent(viewModel: MovieViewModel) {
    viewModel.fetchPopularMovies()
    val movies by remember { derivedStateOf { viewModel.movies } }
    val isLoading by remember { derivedStateOf { viewModel.isLoading } }

    MovieScreen(movies, isLoading)
}


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