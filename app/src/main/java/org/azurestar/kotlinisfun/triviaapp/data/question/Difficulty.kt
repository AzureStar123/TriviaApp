package org.azurestar.kotlinisfun.triviaapp.data.question

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Difficulty(val urlString: String): Parcelable {
    Easy("easy"), Medium("medium"), Hard("hard");
}