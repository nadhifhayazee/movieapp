package com.nadhif.hayazee.common.model

sealed class ResponseState<T> {
    class Loading<T>() : ResponseState<T>()
    class Success<T>() : ResponseState<T>()
    data class SuccessWithData<T>(val data: T) : ResponseState<T>()
    data class Error<T>(val message: String?) : ResponseState<T>()
}
