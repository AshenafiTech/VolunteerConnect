package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.model.EventResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrgSpecificEventsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getOrgSpecificEvents(): Response<EventResponse> {
        return apiService.getEventsForOrg()
    }
}
