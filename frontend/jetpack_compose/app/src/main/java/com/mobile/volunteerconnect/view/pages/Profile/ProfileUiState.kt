package com.mobile.volunteerconnect.view.pages.Profile

import com.mobile.volunteerconnect.data.model.ProfileUser

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: ProfileUser? = null,
    val error: String? = null,
    val isUpdating: Boolean = false,
    val updateSuccess: Boolean = false,
)


