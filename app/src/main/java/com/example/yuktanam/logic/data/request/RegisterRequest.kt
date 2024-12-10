package com.example.yuktanam.logic.data.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirm: String
)
