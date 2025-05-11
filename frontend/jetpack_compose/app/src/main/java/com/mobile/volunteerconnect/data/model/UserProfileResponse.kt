package com.mobile.volunteerconnect.data.model

data class UserProfileResponse(
    val user: UserProfile
)

data class UserProfile(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val skills: List<String>,
    val interests: List<String>
)
