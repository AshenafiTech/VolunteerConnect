package com.mobile.volunteerconnect.view.pages.components

import com.example.volunteerconnectjetpackcompose.ui.theme.Data.ApplicationDataModel.ApplicationStatus

data class Application(
    val status: ApplicationStatus,
    val appliedDate: String,
    val title: String,
    val organization: String,
    val date: String,
    val time: String
)