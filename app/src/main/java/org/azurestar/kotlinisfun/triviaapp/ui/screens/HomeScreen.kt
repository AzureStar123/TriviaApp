package org.azurestar.kotlinisfun.triviaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel

@Composable
fun HomeScreen(navController: NavController, questionViewModel: QuestionViewModel) {
    Scaffold(topBar = {
        TopAppBar(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Home")
        }
    }
    ) {
        Content()
    }
}

@Composable
fun Content() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Take a Quiz", style = MaterialTheme.typography.h3)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Show Options")
        }
    }
}

@Composable
fun ShowOptions() {

}