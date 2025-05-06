package com.mobile.volunteerconnect.data.api

import com.mobile.volunteerconnect.data.model.LoginRequest
import com.mobile.volunteerconnect.data.model.LoginResponse
import com.mobile.volunteerconnect.data.model.SignupRequest
import com.mobile.volunteerconnect.data.model.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    @POST("api/auth/register")
    suspend fun signup(@Body signupRequest: SignupRequest): SignupResponse
}