package org.azurestar.kotlinisfun.triviaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.azurestar.kotlinisfun.triviaapp.ui.screens.Screens

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            navController.navigate(Screens.HomeScreen.name)
        }
        composable(Screens.QuizScreen.name) {
            navController.navigate(Screens.QuizScreen.name)
        }
        composable(Screens.ResultScreen.name) {
            navController.navigate(Screens.ResultScreen.name)
        }
    }
}