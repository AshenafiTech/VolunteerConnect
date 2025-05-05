package com.mobile.volunteerconnect.view.pages.login

import com.mobile.volunteerconnect.data.model.User

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val token: String? = null,
    val user: User? = null
)