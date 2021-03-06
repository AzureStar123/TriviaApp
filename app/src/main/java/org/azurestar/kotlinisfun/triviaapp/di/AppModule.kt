package org.azurestar.kotlinisfun.triviaapp.di

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.azurestar.kotlinisfun.triviaapp.data.repository.QuestionRepository
import org.azurestar.kotlinisfun.triviaapp.data.vm.QuestionViewModel
import org.azurestar.kotlinisfun.triviaapp.retrofit.QuestionApi
import org.azurestar.kotlinisfun.triviaapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi =
        Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuestionApi::class.java)
}