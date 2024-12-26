package com.example.my_pokedex.utils

sealed class Resource<T>(var data : T? = null, var code : Int? = null, var errorResponse: ErrorResponse? = null){
    class Success<T>(data: T? = null) : Resource<T>(data = data)
    class Error<T>(code: Int? = null,  errorResponse: ErrorResponse? = null) : Resource<T>(code= code, errorResponse = errorResponse)
    class Loading<T> : Resource<T>()
}