package com.mobile.volunteerconnect.data.api

import com.mobile.volunteerconnect.data.model.ApplicantItem
import retrofit2.http.GET
import retrofit2.http.Path

interface applicantApi {

    @GET("/api/event/{eventId}/applicants")
    suspend fun getApplicantsByEvent(@Path("eventId") eventId: Int): List<ApplicantItem>

}
