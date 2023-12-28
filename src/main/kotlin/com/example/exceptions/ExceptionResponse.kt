package com.example.exceptions

import org.springframework.http.HttpStatus
import java.util.*

class ExceptionResponse (
    val timeStamp: Date,
    val message: String?,
    val details: String,
    val httpResponseCode: HttpStatus
)