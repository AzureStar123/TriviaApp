package org.azurestar.kotlinisfun.triviaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.azurestar.kotlinisfun.triviaapp.ui.screens.HomeScreen
import org.azurestar.kotlinisfun.triviaapp.ui.screens.QuizScreen
import org.azurestar.kotlinisfun.triviaapp.ui.screens.ResultScreen
import org.azurestar.kotlinisfun.triviaapp.ui.screens.Screens

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(Screens.QuizScreen.name) {
            QuizScreen(navController = navController)
        }
        composable(Screens.ResultScreen.name) {
            ResultScreen(navController = navController)
        }
    }
}