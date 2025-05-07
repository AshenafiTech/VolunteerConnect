package com.mobile.volunteerconnect.data.api

import retrofit2.http.GET

interface applicantApi {

    @GET(ApiConstants.APPLICANTS_ENDPOINT)
    suspend fun getApplicants(): applicant
}