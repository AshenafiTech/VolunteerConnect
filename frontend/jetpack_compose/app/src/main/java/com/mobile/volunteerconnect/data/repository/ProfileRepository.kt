package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.model.DeleteUserProfileResponse
import com.mobile.volunteerconnect.data.model.UserProfileRequest
import com.mobile.volunteerconnect.data.model.UserProfileResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlin.text.startsWith

class ProfileRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUserProfile(token: String, userId: Int): UserProfileResponse {
        return apiService.getUserProfile("Bearer $token", userId)
    }
    suspend fun updateUserProfile(token: String, userId: Int, profileRequest: UserProfileRequest): UserProfileResponse {
        return apiService.updateUserProfile("Bearer $token", userId, profileRequest)
    }
    suspend fun deleteUserProfile(token: String): Result<DeleteUserProfileResponse> {
        return try {
            // Add proper "Bearer" prefix if not already present
            val formattedToken = if (!token.startsWith("Bearer ")) "Bearer $token" else token
            val response = apiService.deleteUserProfile(formattedToken)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
