package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.userApplicationApi
import com.mobile.volunteerconnect.data.model.userApplicationsResponse
import javax.inject.Inject

class userApplicationsRepo @Inject constructor(
    private val api: userApplicationApi
) {
    suspend fun getUserApplications(): List<userApplicationsResponse> {
        return api.getUserApplication().events
    }
}
