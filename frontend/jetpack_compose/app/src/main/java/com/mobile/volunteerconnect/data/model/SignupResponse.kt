package com.mobile.volunteerconnect.data.model

data class SignupResponse(
    val message: String,
    val token: String,
    val user: User
)

