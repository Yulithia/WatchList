package com.example.watchlist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room


class MainActivity : ComponentActivity() {
    //private val viewModel = MovieViewModel()
    private val viewModel: MovieViewModel by viewModels {
        object : ViewModelProvider.AndroidViewModelFactory(application) {}
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val context: Context = applicationContext
        //context.deleteDatabase("app-database")  // Use your actual database name

        // Rebuild the database after deletion
        //val db = Room.databaseBuilder(
          //  context,
            //MovieDatabase::class.java, "app-database"  // Use your actual database class and name
        //)
          //  .fallbackToDestructiveMigration()
            //.build()

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                Navigation(viewModel)
            }
        }

    }
}


