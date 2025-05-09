package com.mobile.volunteerconnect.data.model

data class UserProfileRequest(
    val name: String,
    val email: String,
    val city: String,
    val phone: String,
    val bio: String,
    val skills: List<String>,
    val interests: List<String>
)
