package org.azurestar.kotlinisfun.triviaapp.data.repository

import org.azurestar.kotlinisfun.triviaapp.data.question.DataOrException
import org.azurestar.kotlinisfun.triviaapp.data.question.Difficulty
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionList
import org.azurestar.kotlinisfun.triviaapp.data.question.Topic
import org.azurestar.kotlinisfun.triviaapp.retrofit.QuestionApi
import javax.inject.Inject


class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {
    suspend fun getQuestions(
        limit: Int,
        difficulty: Difficulty,
        topics: List<Topic> = listOf(Topic.GeneralKnowledge)
    ): DataOrException<QuestionList, Exception> {
        val dataOrException = DataOrException<QuestionList, Exception>(null, false, null)
        try {
            val topicStrings = mutableListOf<String>()
            topics.forEach { topicStrings.add(it.urlString) }
            dataOrException.data = questionApi.getQuestions(limit, difficulty.urlString, topicStrings)
        } catch (e: Exception) {
            dataOrException.exception = e
        }
        return dataOrException
    }
}