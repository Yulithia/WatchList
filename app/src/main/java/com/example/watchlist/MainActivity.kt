package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier


class MainActivity : ComponentActivity() {
    private val viewModel = MovieViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel.fetchGenres()

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                Navigation(viewModel)
            }
        }

    }
}
