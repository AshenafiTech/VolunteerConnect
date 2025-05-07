package com.mobile.volunteerconnect.data.model

import com.google.gson.annotations.SerializedName

data class applicantItem(
    @SerializedName("applied_at")
    val appliedAt: String = "",
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("user_id")
    val userId: Int = 0
)