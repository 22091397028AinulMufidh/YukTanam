package com.example.yuktanam.logic.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirm: String
)
