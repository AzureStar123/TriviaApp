package org.azurestar.kotlinisfun.triviaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.azurestar.kotlinisfun.triviaapp.data.question.Difficulty
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionInfo
import org.azurestar.kotlinisfun.triviaapp.data.question.Topic
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel
import org.azurestar.kotlinisfun.triviaapp.utils.fromSliderPos

private const val TAG = "HomeScreen"
const val maxLimit = 20

@Composable
fun HomeScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Home") }) }) {
        Content(navController = navController, questionViewModel = questionViewModel)
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    navController: NavController,
    questionViewModel: QuestionViewModel
) {

    var limit by remember { mutableStateOf(10) }
    var difficulty by remember { mutableStateOf(Difficulty.Medium) }
    var topics = remember { mutableStateListOf(Topic.Geography) }
    var sliderPos by remember { mutableStateOf(0.5f) }
    var showMoreOptions by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val questionInfo = QuestionInfo(limit, difficulty, topics)
            questionViewModel.fetchQuestions(questionInfo)
            navController.navigate("${Screens.QuizScreen}")
        }) {
            Text(text = "Take a Quiz", style = MaterialTheme.typography.h4)
        }
        IconButton(onClick = { showMoreOptions = !showMoreOptions }) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = if (!showMoreOptions) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Show Options"
            )
        }
        if (showMoreOptions) {
            Options(
                limit,
                difficulty,
                topics,
                sliderPos,
                onLimitChanged = { recieveLimit, recieveSliderPos ->
                    limit = recieveLimit
                    sliderPos = recieveSliderPos
                },
                onDifficultyChanged = { difficulty = it },
                onTopicsChanged = { topics = SnapshotStateList<Topic>().apply { addAll(it) } }
            )
        }
    }
}

@Composable
fun Options(
    limit: Int,
    difficulty: Difficulty,
    topics: List<Topic>,
    sliderPos: Float,
    onLimitChanged: (limit: Int, sliderPos: Float) -> Unit,
    onDifficultyChanged: (Difficulty) -> Unit,
    onTopicsChanged: (List<Topic>) -> Unit
) {
    Card(modifier = Modifier.padding(10.dp), elevation = 5.dp) {
        Column {
            Row {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Limit: $limit",
                    style = MaterialTheme.typography.h6
                )
                Slider(
                    value = sliderPos,
                    steps = maxLimit,
                    onValueChange = {
                        onLimitChanged(it fromSliderPos maxLimit, it)
                    }
                )
            }
        }
    }
}