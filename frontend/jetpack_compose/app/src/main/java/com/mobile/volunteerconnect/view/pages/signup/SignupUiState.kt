package com.mobile.volunteerconnect.view.pages.signup

import com.mobile.volunteerconnect.data.model.User

data class SignupUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "Volunteer",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val token: String? = null,
    val user: User? = null
)