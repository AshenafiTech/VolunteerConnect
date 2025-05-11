package com.mobile.volunteerconnect.data.api

import com.mobile.volunteerconnect.data.model.ApplicantItem
import retrofit2.http.GET
import retrofit2.http.Path

interface applicantApi {

    @GET("api/event/{event_id}/applicants")
    suspend fun getApplicantsByEvent(@Path("event_id") eventId: Int): List<ApplicantItem>
}
