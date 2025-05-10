package com.mobile.volunteerconnect.navigation.model


enum class UserRole {
    Volunteer,
    Organization;

    companion object {
        fun fromString(role: String): UserRole =
            when (role.trim().lowercase()) {
                "volunteer" -> Volunteer
                "organization" -> Organization
                else -> throw IllegalArgumentException("Unknown role: $role")
            }
    }
}
