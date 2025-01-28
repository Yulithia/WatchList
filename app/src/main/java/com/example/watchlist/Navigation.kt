package com.example.watchlist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@SuppressLint
@Composable
fun Navigation(viewModel: MovieViewModel)
{
    val navController = rememberNavController()
    Scaffold (
        bottomBar = {BottomMenu(navController = navController)},
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){
                BottomNavGraph(navController = navController, viewModel)

            }
        }
    )
}

@Composable
fun BottomMenu(navController: NavHostController)
{
    val screens = listOf(BottomBar.PopularMoviesScreen, BottomBar.SearchMovieScreen, BottomBar.MyMoviesScreen)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar (containerColor = Color.LightGray)
    {
        screens.forEach{ screen ->
            NavigationBarItem(
                label = { Text(text = screen.title)},
                icon = { Icon(imageVector = screen.icon, contentDescription = "icon") },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {navController.navigate(screen.route)}
            )
        }
    }
}

@Composable
fun BottomNavGraph(navController: NavHostController, viewModel: MovieViewModel) {
    NavHost(navController = navController, startDestination = Screens.PopularMoviesScreen.route) {
        // Popular Movies Screen
        composable(Screens.PopularMoviesScreen.route) {
            PopularMoviesScreen(viewModel, onMovieClick = { movie ->
                navController.navigate(Screens.MovieDetails.createRoute(movie.id))
            })
        }

        // Movie Details Screen
        composable(
            route = Screens.MovieDetails.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            if (movieId != null) {
                val movie = movieId.let { viewModel.findMovieById(it) }
                if (movie != null) {
                    val genreText = movie.genreIds.mapNotNull { viewModel._genres[it] }.joinToString(", ")
                    MovieDetails(movie = movie, genreText = genreText, isFavorite = viewModel.isFavourite(movie),
                        onFavoriteClick = { viewModel.toggleFavourite(it) })
                } else {
                    Text("Movie not found", Modifier.fillMaxSize(), style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                Text("Invalid movie ID", Modifier.fillMaxSize(), style = MaterialTheme.typography.bodyLarge)
            }
        }

        composable(Screens.SearchMovieScreen.route) {
            SearchMovieScreen(viewModel, onMovieClick = { movie ->
                navController.navigate(Screens.MovieDetails.createRoute(movie.id))
            })
        }

        // My Movies Screen
        composable(Screens.MyMoviesScreen.route) {
            MyMoviesScreen(viewModel, onClick = {
                navController.navigate(Screens.FavouriteMoviesScreen.route)
            })
        }

        composable(Screens.FavouriteMoviesScreen.route) {
            FavouriteMoviesScreen(viewModel, onMovieClick = { movie->
                navController.navigate((Screens.MovieDetails.createRoute(movie.id)))
            })
        }
    }
}
