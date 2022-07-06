package org.azurestar.kotlinisfun.triviaapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.azurestar.kotlinisfun.triviaapp.components.NumberPicker
import org.azurestar.kotlinisfun.triviaapp.data.question.Difficulty
import org.azurestar.kotlinisfun.triviaapp.data.question.Topic
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel

private const val TAG = "HomeScreen"

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

    var limit by remember { mutableStateOf(questionViewModel.questionInfo.limit) }
    var difficulty by remember { mutableStateOf(questionViewModel.questionInfo.difficulty) }
    val topics =
        remember { mutableStateListOf(*questionViewModel.questionInfo.topics.toTypedArray()) }

    questionViewModel.questionInfo.limit = limit
    questionViewModel.questionInfo.difficulty = difficulty
    questionViewModel.questionInfo.topics = topics.toList()

    var showMoreOptions by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (questionViewModel.questionInfo.topics.isEmpty()) return@Button
            questionViewModel.deleteQuestions()
            questionViewModel.fetchQuestions()
            navController.navigate("${Screens.QuizScreen}")
        }) {
            Text(text = "Take a Quiz", style = MaterialTheme.typography.h4)
        }
        IconButton(onClick = { showMoreOptions = !showMoreOptions }) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = if (!showMoreOptions) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                contentDescription = "Show Options"
            )
        }
        AnimatedVisibility(showMoreOptions) {
            Options(
                limit,
                difficulty,
                topics,
                onLimitChanged = { limit = it },
                onDifficultyChanged = { difficulty = it },
                onAdd = { topics.add(it) },
                onRemove = { topics.remove(it) }
            )
        }
    }
}

@Composable
fun Options(
    limit: Int,
    difficulty: Difficulty,
    topics: List<Topic>,
    onLimitChanged: (Int) -> Unit,
    onDifficultyChanged: (Difficulty) -> Unit,
    onAdd: (Topic) -> Unit,
    onRemove: (Topic) -> Unit
) {
    Card(modifier = Modifier.padding(10.dp), elevation = 5.dp) {
        Column {
            LimitPicker(limit = limit, onLimitChanged = onLimitChanged)
            DifficultyDropDown(difficulty = difficulty, onDifficultyChanged = onDifficultyChanged)
            TopicCheckBoxes(topics = topics, onAdd = onAdd, onRemove = onRemove)
        }
    }
}

@Composable
fun LimitPicker(limit: Int, onLimitChanged: (Int) -> Unit) {
    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Limit: ",
            style = MaterialTheme.typography.h6
        )
        NumberPicker(onValueChange = onLimitChanged, defaultValue = limit, min = 1, max = 20)
    }
}

@Composable
fun DifficultyDropDown(difficulty: Difficulty, onDifficultyChanged: (Difficulty) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Difficulty: ",
            style = MaterialTheme.typography.h6
        )
        Box {
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = difficulty.name)
                Icon(
                    imageVector = if (!expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                    contentDescription = "Difficulty Drop Down"
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                Difficulty.values().forEach {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onDifficultyChanged(it)
                    }) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicCheckBoxes(topics: List<Topic>, onAdd: (Topic) -> Unit, onRemove: (Topic) -> Unit) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = "Topics:",
            style = MaterialTheme.typography.h6
        )
        Card(modifier = Modifier.padding(top = 5.dp), elevation = 5.dp) {
            LazyVerticalGrid(cells = GridCells.Adaptive(150.dp)) {
                items(Topic.values()) { topic ->
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var checked by remember { mutableStateOf(topics.contains(topic)) }
                        Text(text = "${topic.readable}: ")
                        Checkbox(checked = checked, onCheckedChange = {
                            checked = it
                            if (checked) onAdd(topic) else onRemove(topic)
                        })
                    }
                }
            }
        }
    }
}