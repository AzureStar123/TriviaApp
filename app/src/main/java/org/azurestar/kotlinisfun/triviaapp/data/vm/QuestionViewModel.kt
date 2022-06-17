package org.azurestar.kotlinisfun.triviaapp.data.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.azurestar.kotlinisfun.triviaapp.data.question.*
import org.azurestar.kotlinisfun.triviaapp.data.repository.QuestionRepository
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository): ViewModel() {
    val questions by mutableStateOf(DataOrException<QuestionList, Exception>(null, false, null))

    fun getQuestions(limit: Int = 10, difficulty: Difficulty = Difficulty.Medium, topics: List<Topic> = listOf(Topic.GeneralKnowledge)) {
        viewModelScope.launch {
            questions.loading = true
            try {
                questions.data = questionRepository.getQuestions(limit, difficulty, topics)
                if (questions.data!!.isNotEmpty()) {
                    questions.loading = false
                }
            } catch (e: Exception) {
                questions.exception = e
            }
        }
    }
}