package org.azurestar.kotlinisfun.triviaapp.data.question

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Topic(val urlString: String): Parcelable {
    Geography("geography"),
    History("history"),
    SportAndLeisure("sport_and_leisure"),
    FoodAndDrink("food_and_drink")
}