package org.azurestar.kotlinisfun.triviaapp.ui.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.azurestar.kotlinisfun.triviaapp.data.question.Question
import org.azurestar.kotlinisfun.triviaapp.data.quiz.QuizResult
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel

private const val TAG = "QuizScreen"

@Composable
fun QuizScreen(
    navController: NavController,
    questionViewModel: QuestionViewModel
) {

    var currentQuestionNo by remember { mutableStateOf(1) }
    val questionSize = questionViewModel.questionInfo.limit

    BackHandler {
        navController.popBackStack()
    }
    Scaffold(
        topBar = { TopQuizBar(navController = navController) },
        bottomBar = {
            BottomQuizBar(
                currentQuestionNo = currentQuestionNo,
                questionSize = questionSize
            )
        }

    ) {
        QuizContent(navController, questionViewModel) { currentQuestionNo = it }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuizContent(
    navController: NavController,
    questionViewModel: QuestionViewModel,
    onQuestionChanged: (questionNo: Int) -> Unit
) {

    var questionNo by remember { mutableStateOf(0) }
    val questions = questionViewModel.questions
    val quizResults = remember { mutableStateListOf<QuizResult>() }

    AnimatedContent(
        targetState = questionNo,
        transitionSpec = { animatedHorizontalTransitionSpec() }) {
        if (questions.data == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val question = questions.data!![questionNo]
            Column(modifier = Modifier.fillMaxSize()) {
                question.apply {
                    QuizQuestion(quizQuestion = this) { userAnswer, answers ->
                        quizResults.add(
                            QuizResult(
                                this.question,
                                correctAnswer,
                                userAnswer,
                                answers
                            )
                        )
                        if (questionNo + 1 < questions.data!!.size) {
                            questionNo++
                        } else {
                            questionViewModel.quizResults = quizResults
                            navController.navigate(Screens.ResultScreen.name)
                        }
                        onQuestionChanged(questionNo + 1)
                        Log.d(TAG, "QuizContent: ${quizResults.size}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizQuestion(
    quizQuestion: Question,
    onAnswerClick: (userAnswer: String, answers: List<String>) -> Unit
) {
    with(quizQuestion) {
        initializeAnswers()
        answers = answers!!.shuffled()
        Text(
            modifier = Modifier.padding(15.dp),
            text = question,
            style = MaterialTheme.typography.h6
        )
        LazyColumn {
            items(answers!!, key = { it }) {
                Button(
                    modifier = Modifier
                        .padding(15.dp)
                        .width(200.dp)
                        .animateItemPlacement(),
                    onClick = { onAnswerClick(it, answers!!) }
                ) {
                    Text(text = it, style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

@Composable
fun TopQuizBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "QuizScreen") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomQuizBar(currentQuestionNo: Int, questionSize: Int) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = currentQuestionNo,
                transitionSpec = { animatedVerticalTransitionSpec() }) {
                Text(
                    text = "$it",
                    style = MaterialTheme.typography.h6
                )
            }
            Text(
                text = "/$questionSize",
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<Int>.animatedHorizontalTransitionSpec() =
    if (targetState > initialState) {
        slideInHorizontally(animationSpec = tween(500)) { width -> width } + fadeIn() with
                slideOutHorizontally(animationSpec = tween(500)) { width -> -width } + fadeOut()
    } else {
        slideInHorizontally(animationSpec = tween(500)) { width -> -width } + fadeIn() with
                slideOutHorizontally(animationSpec = tween(500)) { width -> width } + fadeOut()
    } using SizeTransform(clip = false)

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<Int>.animatedVerticalTransitionSpec() =
    if (targetState > initialState) {
        slideInVertically(animationSpec = tween(500)) { height -> height } + fadeIn() with
                slideOutVertically(animationSpec = tween(500)) { height -> -height } + fadeOut()
    } else {
        slideInVertically(animationSpec = tween(500)) { height -> -height } + fadeIn() with
                slideOutVertically(animationSpec = tween(500)) { height -> height } + fadeOut()
    } using SizeTransform(clip = false)
