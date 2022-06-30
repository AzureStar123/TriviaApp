package org.azurestar.kotlinisfun.triviaapp.data.question

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Topic(val urlString: String, val readable: String): Parcelable {
    Geography("geography", "Geography"),
    History("history", "History"),
    SportAndLeisure("sport_and_leisure", "Sport And Leisure"),
    FoodAndDrink("food_and_drink", "Food And Drink"),
    Science("science", "Science")
}