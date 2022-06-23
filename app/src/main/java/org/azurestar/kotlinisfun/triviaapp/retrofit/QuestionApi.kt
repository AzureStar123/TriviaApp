package org.azurestar.kotlinisfun.triviaapp.retrofit

import org.azurestar.kotlinisfun.triviaapp.data.question.QuestionList
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("questions")
    suspend fun getQuestions(@Query("limit") limit: Int, @Query("difficulty") difficulty: String, @Query("categories") topics: List<String>) = QuestionList()
}