package com.mobile.volunteerconnect.data.model

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String // "Volunteer" or "Organization"
)