package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.applicantApi
import com.mobile.volunteerconnect.data.model.applicantItem
import javax.inject.Inject

class applicantsRepo @Inject constructor(
    private val api: applicantApi
) {

    suspend fun getApplicants(): List<applicantItem> {
        return api.getApplicants()
    }
}
