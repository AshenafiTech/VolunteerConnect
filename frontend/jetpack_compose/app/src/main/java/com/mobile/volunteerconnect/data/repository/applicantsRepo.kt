package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.applicantApi
import com.mobile.volunteerconnect.data.model.ApplicantItem
import javax.inject.Inject

class applicantsRepo @Inject constructor(
    private val api: applicantApi
) {

    suspend fun getApplicants(): List<ApplicantItem> {
        return api.getApplicants()
    }
}
