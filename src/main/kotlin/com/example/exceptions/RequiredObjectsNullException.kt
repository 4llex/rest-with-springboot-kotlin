package com.example.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectsNullException: RuntimeException {
    constructor(): super("It is not allowed to persost a null object")
    constructor(exception: String?): super(exception)
}