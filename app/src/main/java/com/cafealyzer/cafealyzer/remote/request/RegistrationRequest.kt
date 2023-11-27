package com.cafealyzer.cafealyzer.remote.request

data class RegistrationRequest(
    val name: String,
    val email: String,
    val password: String
)
