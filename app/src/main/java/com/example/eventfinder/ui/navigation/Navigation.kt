package com.example.eventfinder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventfinder.ui.MainScreen
import com.example.eventfinder.ui.WebViewScreen
import com.example.eventfinder.ui.viewmodels.EventViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            val viewModel: EventViewModel = hiltViewModel()

            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable("webViewScreen/{url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""

            WebViewScreen(navController = navController, url = url)
        }
    }
}