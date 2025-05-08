package com.mobile.volunteerconnect.data.model

data class CreateEventRequest(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val date: String,
    val time: String,
    val location: String,
    val spotsLeft: Int,
    val image: String,
    val description: String,
    val requirements: String,
    val additionalInfo: String,
    val contactPhone: String,
    val contactEmail: String,
    val contactTelegram: String
)