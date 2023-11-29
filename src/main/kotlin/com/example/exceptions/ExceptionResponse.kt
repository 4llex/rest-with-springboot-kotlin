package com.example.exceptions

import java.util.*

data class ExceptionResponse (
    val timeStamp: Date,
    val message: String?,
    val details: String
)