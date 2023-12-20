package com.example.data.vo.v1

import java.util.Date

// Esse VO é utilizado para responta do login
data class TokenVO(
    val username: String? = null,
    val authenticated: Boolean? = null,
    val created: Date? = null,
    val expiration: Date? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)
