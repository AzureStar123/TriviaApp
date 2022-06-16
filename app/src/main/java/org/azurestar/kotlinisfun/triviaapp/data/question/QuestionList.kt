package org.azurestar.kotlinisfun.triviaapp.data.question

typealias QuestionList = ArrayList<Question>

data class Question(
    val category: String,
    val correctAnswer: String,
    val difficulty: String,
    val id: String,
    val incorrectAnswers: List<String>,
    val question: String,
    val tags: List<String>,
    val type: String
)
