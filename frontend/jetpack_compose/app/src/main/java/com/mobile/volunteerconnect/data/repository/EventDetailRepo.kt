package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.model.EventData
import com.mobile.volunteerconnect.data.api.ApiService
import javax.inject.Inject

class EventDetailRepo @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getEventById(eventId: Int): EventData? {
        val response = apiService.getEventById(eventId)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Failed to load event: ${response.code()}")
        }
    }

    suspend fun applyToEvent(eventId: Int) {
        val response = apiService.applyToEvent(eventId)

        if (response.isSuccessful) {
        } else if (response.code() == 409) {
            throw Exception("Existing application found")
        } else {
            throw Exception("Application failed: ${response.code()}")
        }
    }



}
