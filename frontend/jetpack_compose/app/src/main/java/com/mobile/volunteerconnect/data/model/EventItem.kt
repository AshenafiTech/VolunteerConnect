package com.mobile.volunteerconnect.data.model

data class EventItem(
    val id: Int,
    val uuid: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val date: String,
    val time: String?,
    val location: String,
    val spotsLeft: Int,
    val image: String?
)

