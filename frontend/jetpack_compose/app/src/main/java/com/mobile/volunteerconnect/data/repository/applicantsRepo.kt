package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.applicantApi
import com.mobile.volunteerconnect.data.model.ApplicantItem
import javax.inject.Inject

class applicantsRepo @Inject constructor(
    private val api: applicantApi
) {

    // fetch applicants for a specific event by eventId
    suspend fun getApplicantsByEvent(eventId: Int): List<ApplicantItem> {
        return api.getApplicantsByEvent(eventId)
    }
}
