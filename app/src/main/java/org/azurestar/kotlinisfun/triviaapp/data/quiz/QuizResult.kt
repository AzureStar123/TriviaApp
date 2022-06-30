package org.azurestar.kotlinisfun.triviaapp.data.quiz

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize


@Parcelize
class QuizResult(
    val question: String,
    val correctAnswer: String,
    val userAnswer: String,
    val answers: List<String>,
    val correct: Boolean = userAnswer == correctAnswer
): Parcelable