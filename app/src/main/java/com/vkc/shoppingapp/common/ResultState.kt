package com.vkc.shoppingapp.common

sealed class ResultState<out T> {
    data class Success<T>(val data :T) :ResultState<T>()
    data class Error<out T>(val message :String) :ResultState<T>()
    data object Loading :ResultState<Nothing>()
}