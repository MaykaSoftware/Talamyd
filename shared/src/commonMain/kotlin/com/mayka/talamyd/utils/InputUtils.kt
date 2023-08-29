package com.mayka.talamyd.utils

fun emailRegex(email: String): Boolean {
    val regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    return !email.matches(regex.toRegex())
}