package com.mobile.volunteerconnect.data.api

import com.mobile.volunteerconnect.data.model.CreateEventRequest
import com.mobile.volunteerconnect.data.model.DeleteUserProfileResponse
import com.mobile.volunteerconnect.data.model.EventResponse
import com.mobile.volunteerconnect.data.model.LoginRequest
import com.mobile.volunteerconnect.data.model.LoginResponse
import com.mobile.volunteerconnect.data.model.SignupRequest
import com.mobile.volunteerconnect.data.model.SignupResponse
import com.mobile.volunteerconnect.data.model.UserProfileRequest
import com.mobile.volunteerconnect.data.model.UserProfileResponse
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

    @GET("api/users/{id}/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    ): UserProfileResponse
    // to edit a resource
    @PUT("api/users/{id}")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Path("id") userId: Int,
        @Body profileRequest: UserProfileRequest
    ): UserProfileResponse
    @DELETE("api/user")  // Matches your API specification
    suspend fun deleteUserProfile(
        @Header("Authorization") token: String
    ): DeleteUserProfileResponse


    @GET("/api/events")
    suspend fun getOrgEvents(): EventResponse

    @DELETE("api/applications/{eventId}")
    suspend fun deleteApplication(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String
    ): Response<Unit>
}