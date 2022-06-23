package org.azurestar.kotlinisfun.triviaapp.data.vm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.azurestar.kotlinisfun.triviaapp.data.question.DataOrException
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionInfo
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionList
import org.azurestar.kotlinisfun.triviaapp.data.quiz.QuizResults
import org.azurestar.kotlinisfun.triviaapp.data.repository.QuestionRepository
import javax.inject.Inject

private const val TAG = "QuestionViewModel"

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {

    var quizResults = mutableListOf<QuizResults>()

    var questions by mutableStateOf(DataOrException<QuestionList, Exception>(null, false, null))
        private set

    fun fetchQuestions(questionInfo: QuestionInfo) {
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