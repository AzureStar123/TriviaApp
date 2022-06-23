package org.azurestar.kotlinisfun.triviaapp.data.question


data class DataOrException<T, E: Exception>(
    var data: T? = null,
    var loading: Boolean,
    var exception: E? = null
)