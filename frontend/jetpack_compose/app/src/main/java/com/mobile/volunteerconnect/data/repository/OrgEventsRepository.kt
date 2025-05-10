package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.model.EventItem
import javax.inject.Inject


class OrgEventsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getOrgEvents(): List<EventItem> {
        return apiService.getOrgEvents().events
    }
}
