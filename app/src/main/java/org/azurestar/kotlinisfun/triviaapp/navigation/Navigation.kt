package org.azurestar.kotlinisfun.triviaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel
import org.azurestar.kotlinisfun.triviaapp.ui.screens.HomeScreen
import org.azurestar.kotlinisfun.triviaapp.ui.screens.QuizScreen
import org.azurestar.kotlinisfun.triviaapp.ui.screens.ResultScreen
import org.azurestar.kotlinisfun.triviaapp.ui.screens.Screens

@Composable
fun Navigation(questionViewModel: QuestionViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(navController = navController, questionViewModel = questionViewModel)
        }
        composable(Screens.QuizScreen.name){
            QuizScreen(navController = navController, questionViewModel = questionViewModel)
        }
        composable(Screens.ResultScreen.name) {
            ResultScreen(navController = navController, questionViewModel = questionViewModel)
        }
    }
}