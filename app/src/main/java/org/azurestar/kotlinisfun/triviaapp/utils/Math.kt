package org.azurestar.kotlinisfun.triviaapp.utils

infix fun Int.toSliderPos(sliderLength: Int) = toFloat() / sliderLength
infix fun Float.fromSliderPos(sliderLength: Int) = (this * sliderLength).toInt()