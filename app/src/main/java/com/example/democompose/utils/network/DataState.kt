package com.example.democompose.utils.network

/**
 * state for processing api response Loading, Success and Error
 */
sealed class DataState<out R> {
    class Success<out T>(val data: T) : DataState<T>()
    class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}