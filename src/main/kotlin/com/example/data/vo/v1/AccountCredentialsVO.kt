package com.example.data.vo.v1

// Esse VO é utilizado para entrega na aplicação (login)
data class AccountCredentialsVO(
    val username: String? = null,
    val password: String? = null
)
