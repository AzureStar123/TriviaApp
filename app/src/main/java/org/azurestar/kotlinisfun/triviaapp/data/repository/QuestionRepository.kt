package org.azurestar.kotlinisfun.triviaapp.data.repository

import android.util.Log
import org.azurestar.kotlinisfun.triviaapp.data.question.DataOrException
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionInfo
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionList
import org.azurestar.kotlinisfun.triviaapp.data.question.Topic
import org.azurestar.kotlinisfun.triviaapp.retrofit.QuestionApi
import javax.inject.Inject

private const val TAG = "QuestionRepository"

class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {
    suspend fun getQuestions(
        questionInfo: QuestionInfo
    ): DataOrException<QuestionList, Exception> {
        val dataOrException = DataOrException<QuestionList, Exception>(null, null)
        try {
            val listOfTopicStrings =
                List(questionInfo.topics.size) { questionInfo.topics[it].urlString }.toString()
                    .replace(Regex("[\\[\\]]"), "")
            dataOrException.data = questionApi.getQuestions(
                questionInfo.limit,
                questionInfo.difficulty.urlString,
                listOfTopicStrings
            )
        } catch (e: Exception) {
            dataOrException.exception = e
        }
        return dataOrException
    }
}