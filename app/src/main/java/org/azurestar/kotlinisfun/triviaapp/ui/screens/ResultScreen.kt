package org.azurestar.kotlinisfun.triviaapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.azurestar.kotlinisfun.triviaapp.data.quiz.QuizResults
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel

@Composable
fun ResultScreen(
    navController: NavController,
    questionViewModel: QuestionViewModel,
) {

    BackHandler {
        navController.popBackStack(Screens.HomeScreen.name, false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Results") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack(
                            Screens.HomeScreen.name,
                            false
                        )
                    }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.popBackStack(
                    Screens.HomeScreen.name,
                    false
                )
            }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            }
        }
    ) {
        ResultContent(quizResults = questionViewModel.quizResults, navController = navController)
    }
}

@Composable
fun ResultContent(quizResults: List<QuizResults>, navController: NavController) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            text = "Score: ${quizResults.filter { it.correct }.size}/${quizResults.size}",
            style = MaterialTheme.typography.h4
        )
        ResultsDisplay(quizResults = quizResults)
    }
}

@Composable
fun ResultsDisplay(quizResults: List<QuizResults>) {
    LazyColumn {
        items(quizResults) {
            Card(modifier = Modifier.padding(20.dp), elevation = 5.dp) {
                Column {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = it.question,
                        style = MaterialTheme.typography.h6
                    )
                    it.answers.forEach { answer ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "• $answer",
                                style = MaterialTheme.typography.h6
                            )
                            if (it.correctAnswer == answer) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Correct"
                                )
                            }
                            if (it.userAnswer == answer && answer != it.correctAnswer) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Wrong"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}