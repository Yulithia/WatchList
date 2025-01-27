package com.example.watchlist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
fun BottomNavGraph(navController: NavHostController, viewModel: MovieViewModel)
{
    NavHost(navController = navController, startDestination = Screens.PopularMoviesScreen.route)
    {
        composable(route = Screens.PopularMoviesScreen.route) { PopularMoviesScreen(viewModel) }
        composable(route = Screens.SearchMovieScreen.route) { SearchMovieScreen(viewModel) }
        composable(route = Screens.MyMoviesScreen.route) { MyMoviesScreen(viewModel) }


    }
}
