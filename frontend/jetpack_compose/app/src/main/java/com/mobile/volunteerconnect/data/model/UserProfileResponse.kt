package com.mobile.volunteerconnect.data.model


// UserProfileResponse.kt
data class UserProfileResponse(
    val message: String,
    val user: ProfileUser
)
data class ProfileUser(
    val id: Int,
    val name: String?,
    val email: String?,  // Added
    val phone: String?,
    val city: String?,
    val bio: String?,
    val attendedEvents: Int?,  // From API
    val hoursVolunteered: Int?, // From API
    val skills: List<String>?,
    val interests: List<String>?
)
