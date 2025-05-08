package com.mobile.volunteerconnect.data.api

import com.mobile.volunteerconnect.data.model.CreateEventRequest
import com.mobile.volunteerconnect.data.model.LoginRequest
import com.mobile.volunteerconnect.data.model.LoginResponse
import com.mobile.volunteerconnect.data.model.SignupRequest
import com.mobile.volunteerconnect.data.model.SignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @POST("api/events/create")
    suspend fun createEvent(@Header("Authorization") token: String, @Body event: CreateEventRequest): Response<Unit>

    @GET("/api/checkUser")
    suspend fun checkUser(@Header("Authorization") token: String): Response<Unit>
}