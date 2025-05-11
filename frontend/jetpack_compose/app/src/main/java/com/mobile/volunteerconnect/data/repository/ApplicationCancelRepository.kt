package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApplicationCancelRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {
    suspend fun deleteApplication(eventId: String): Response<Unit> {
        val token = userPreferences.getToken() ?: return Response.error(403, ResponseBody.create(null, "No Token"))

        // Making the API call to delete the application
        val response = apiService.deleteApplication("Bearer $token", eventId)

        //  error handling for the delete request
        if (!response.isSuccessful) {
            return Response.error(response.code(), response.errorBody() ?: ResponseBody.create(null, "Unknown error"))
        }

        return response
    }
}
