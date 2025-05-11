package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.model.UserProfileResponse
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import retrofit2.Response
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {
    suspend fun getUserProfile(userId: Int): UserProfileResponse? {
        // Fetch the token from UserPreferences
        val token = userPreferences.getToken()

        // If the token is available, call the API
        token?.let {
            val response: Response<UserProfileResponse> = apiService.getUserProfile(userId, "Bearer $it")
            return if (response.isSuccessful) {
                response.body()  // Return the user profile response
            } else {
                null  // Handle error or return null if not successful
            }
        }
        return null  // Return null if token is not available
    }
}
