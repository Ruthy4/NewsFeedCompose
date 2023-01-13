package com.ifycode.newsfeed.util

sealed class Resource<out T> {
    data class Success<out T>(val value: T): Resource<T>()
    data class Loading<out T>(val value: String?) : Resource<T>()
    data class Error(val code: Int? = null, val error: String): Resource<Nothing>()
}



