package com.mobile.volunteerconnect.data.repository


import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.model.CreateEventRequest
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun createEvent(token: String, event: CreateEventRequest) {
        apiService.createEvent("Bearer $token", event)
    }
}
