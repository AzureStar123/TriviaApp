package org.azurestar.kotlinisfun.triviaapp.retrofit

import org.azurestar.kotlinisfun.triviaapp.data.question.Difficulty
import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionList
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("questions/?limit={limit}&difficulty={difficulty}")
    suspend fun getQuestions(@Path("limit") limit: Int = 10, @Path("difficulty") difficulty: String) = QuestionList()
}