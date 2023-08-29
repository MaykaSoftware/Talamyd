package com.mayka.talamyd.common.util

sealed class MyResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Error<T>(data: T? = null, message: String) : MyResult<T>(data, message)
    class Success<T>(data: T) : MyResult<T>(data)
}