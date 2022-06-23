package org.azurestar.kotlinisfun.triviaapp.data.repository

import org.azurestar.kotlinisfun.triviaapp.data.question.*
import org.azurestar.kotlinisfun.triviaapp.retrofit.QuestionApi
import javax.inject.Inject


class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {
    suspend fun getQuestions(
        questionInfo: QuestionInfo
    ): DataOrException<QuestionList, Exception> {
        val dataOrException = DataOrException<QuestionList, Exception>(null, false, null)
        try {
            val topicStrings = mutableListOf<String>()
            questionInfo.topics.forEach { topicStrings.add(it.urlString) }
            dataOrException.data = questionApi.getQuestions(questionInfo.limit, questionInfo.difficulty.urlString, topicStrings)
        } catch (e: Exception) {
            dataOrException.exception = e
        }
        return dataOrException
    }
}