package com.cafealyzer.cafealyzer.remote.request

data class RegistrationRequest(
    val email: String,
    val password: String,
    val username: String
)
