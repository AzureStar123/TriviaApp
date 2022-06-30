package org.azurestar.kotlinisfun.triviaapp.data.question

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.parcelize.Parcelize

@Parcelize
class QuestionInfo(var limit: Int, var difficulty: Difficulty, var topics: List<Topic>): Parcelable