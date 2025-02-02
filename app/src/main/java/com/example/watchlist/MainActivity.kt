package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider


class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels {
        object : ViewModelProvider.AndroidViewModelFactory(application) {}
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                Navigation(viewModel)
            }
        }

    }
}


