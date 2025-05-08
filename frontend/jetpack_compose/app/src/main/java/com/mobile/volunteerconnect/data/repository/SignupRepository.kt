package com.mobile.volunteerconnect.data.repository

import com.mobile.volunteerconnect.data.api.ApiService
import com.mobile.volunteerconnect.data.model.SignupRequest
import com.mobile.volunteerconnect.data.model.SignupResponse
import retrofit2.Response

import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun signup(signupRequest: SignupRequest): Response<SignupResponse> {
        return apiService.signup(signupRequest)
    }
}