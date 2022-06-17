package org.azurestar.kotlinisfun.triviaapp.data.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.azurestar.kotlinisfun.triviaapp.data.question.DataOrException
import org.azurestar.kotlinisfun.triviaapp.data.question.Difficulty
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionList
import org.azurestar.kotlinisfun.triviaapp.data.question.Topic
import org.azurestar.kotlinisfun.triviaapp.data.repository.QuestionRepository
import javax.inject.Inject

private const val TAG = "QuestionViewModel"

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {

    var questions by mutableStateOf(DataOrException<QuestionList, Exception>(null, false, null))
    private set

    fun fetchQuestions(
        limit: Int = 10,
        difficulty: Difficulty = Difficulty.Medium,
        topics: List<Topic> = listOf(Topic.GeneralKnowledge)
    ) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "fetchQuestions: Start")
                questions = questionRepository.getQuestions(limit, difficulty, topics)
                if (questions.data!!.isNotEmpty()) {
                    questions.loading = false
                }
                Log.d(TAG, "fetchQuestions: End ${questions.data?.size}")
            } catch (e: Exception) {
                questions.exception = e
            }
        }
    }
}