package com.mobile.volunteerconnect.data.api

import com.mobile.volunteerconnect.data.model.userAppliedEvent
import retrofit2.http.GET

interface userApplicationApi {
    @GET(ApiConstants.USER_APPLICATIONS_ENDPOINT)

    suspend fun getUserApplication(): userAppliedEvent
}


