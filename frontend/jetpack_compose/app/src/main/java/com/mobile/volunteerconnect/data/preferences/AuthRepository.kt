package com.mobile.volunteerconnect.data.preferences

import com.mobile.volunteerconnect.data.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    internal val userPreferences: UserPreferences
) {
    suspend fun verifyToken(): Boolean {
        return try {
            val token = userPreferences.getToken() ?: return false
            val response = apiService.checkUser("Bearer $token")
            print(response)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}