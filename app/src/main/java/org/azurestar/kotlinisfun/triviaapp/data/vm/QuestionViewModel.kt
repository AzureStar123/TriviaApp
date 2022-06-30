package org.azurestar.kotlinisfun.triviaapp.data.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.azurestar.kotlinisfun.triviaapp.data.question.*
import org.azurestar.kotlinisfun.triviaapp.data.quiz.QuizResult
import org.azurestar.kotlinisfun.triviaapp.data.repository.QuestionRepository
import javax.inject.Inject

private const val TAG = "QuestionViewModel"

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {

    var quizResults = mutableListOf<QuizResult>()

    var questions by mutableStateOf(DataOrException<QuestionList, Exception>(null, false, null))
        private set

    var questionInfo by mutableStateOf(QuestionInfo(10, Difficulty.Medium, mutableListOf(Topic.Geography)))

    fun fetchQuestions(questionInfo: QuestionInfo = this.questionInfo) {
        viewModelScope.launch {
                questions = questionRepository.getQuestions(questionInfo)
                if(questions.exception != null) throw questions.exception!!
                if (questions.data!!.isNotEmpty()) {
                    questions.loading = false
                }
            Log.d(TAG, "fetchQuestions: Done")
        }
    }

    fun deleteQuestions() {
        questions = DataOrException(null, false, null)
    }
}