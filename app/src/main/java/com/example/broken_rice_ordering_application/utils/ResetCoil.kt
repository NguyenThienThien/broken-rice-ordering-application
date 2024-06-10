package com.example.broken_rice_ordering_application.utils

fun String.addQueryParameter(): String {
    return this + "?timestamp=${System.currentTimeMillis()}"
}