package com.mobile.volunteerconnect.view.pages.createpost

data class CreatePostUiState(
    val title: String = "",
    val subtitle: String = "",
    val category: String = "Volunteer",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val spotsLeft: String = "",
    val image: String = "",
    val description: String = "",
    val requirements: List<String> = listOf(""),
    val additionalInfo: String = "",
    val contactPhone: String = "",
    val contactEmail: String = "",
    val contactTelegram: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)