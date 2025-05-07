package com.mobile.volunteerconnect.view.pages.components

data class Application(
    val status: ApplicationStatus,
    val appliedDate: String,
    val title: String,
    val organization: String,
    val date: String,
    val time: String
)