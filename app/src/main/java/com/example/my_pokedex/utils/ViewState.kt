package com.example.my_pokedex.utils

sealed class ViewState<out T : Any>
class Success<out T : Any>(val data: Any? = null) : ViewState<T>()
class Error<out T : Any>(val code: Int? = null, val errorResponse: ErrorResponse? = null) : ViewState<T>()
class Loading<out T : Any> (val data: Any? = null): ViewState<T>()