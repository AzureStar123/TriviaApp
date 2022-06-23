package org.azurestar.kotlinisfun.triviaapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.azurestar.kotlinisfun.triviaapp.data.question.Question
import org.azurestar.kotlinisfun.triviaapp.data.quiz.QuizResults
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel

private const val TAG = "QuizScreen"

@Composable
fun QuizScreen(
    navController: NavController,
    questionViewModel: QuestionViewModel
) {

    BackHandler {
        navController.popBackStack()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "QuizScreen") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        QuizContent(navController, questionViewModel)
    }
}

@Composable
fun QuizContent(navController: NavController, questionViewModel: QuestionViewModel) {

    val questions = questionViewModel.questions
    val quizResults = remember { mutableStateListOf<QuizResults>() }
    var currentQuestionNo by remember { mutableStateOf(0) }

    if (questions.loading || questions.data == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val question = questions.data!![currentQuestionNo]
        question.apply {
            QuizQuestion(quizQuestion = this, onAnswerClick = { userAnswer, answers ->
                quizResults.add(
                    QuizResults(
                        this.question,
                        correctAnswer,
                        userAnswer,
                        answers
                    )
                )
                if (currentQuestionNo + 1 < questions.data!!.size) {
                    currentQuestionNo++
                } else {
                    questionViewModel.quizResults = quizResults
                    questionViewModel.deleteQuestions()
                    navController.navigate(Screens.ResultScreen.name)
                }
            })
        }
    }
}

@Composable
fun QuizQuestion(
    quizQuestion: Question,
    onAnswerClick: (userAnswer: String, answers: List<String>) -> Unit
) {
    with(quizQuestion) {

        val answers = mutableListOf(*incorrectAnswers.toTypedArray(), correctAnswer)
        answers.shuffle()
        Column {
            Text(
                modifier = Modifier.padding(15.dp),
                text = question,
                style = MaterialTheme.typography.h6
            )
            answers.forEach {
                Button(modifier = Modifier
                    .padding(5.dp)
                    .width(200.dp), onClick = {
                    onAnswerClick(it, answers)
                }) {
                    Text(text = it, style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}