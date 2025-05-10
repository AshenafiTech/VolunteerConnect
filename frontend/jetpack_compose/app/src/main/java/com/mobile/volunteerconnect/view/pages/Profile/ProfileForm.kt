package com.mobile.volunteerconnect.view.pages.Profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mobile.volunteerconnect.data.model.ProfileUser
import com.mobile.volunteerconnect.data.model.UserProfileRequest
import kotlin.collections.joinToString
import kotlin.collections.map
import kotlin.text.split
import kotlin.text.trim

@Composable
fun ProfileForm(
    profile: ProfileUser,
    onSave: (UserProfileRequest) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    // State for all editable fields
    var name by remember { mutableStateOf(profile.name ?: "") }
    var email by remember { mutableStateOf(profile.email ?: "") }
    var phone by remember { mutableStateOf(profile.phone ?: "") }
    var city by remember { mutableStateOf(profile.city ?: "") }
    var bio by remember { mutableStateOf(profile.bio ?: "") }
    var skills by remember { mutableStateOf(profile.skills?.joinToString(", ") ?: "") }
    var interests by remember { mutableStateOf(profile.interests?.joinToString(", ") ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Personal Information Section
        Text("Personal Information", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        EditableTextField(
            label = "Full Name",
            value = name,
            onValueChange = { name = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        EditableTextField(
            label = "Email",
            value = email,
            keyboardType = KeyboardType.Email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        EditableTextField(
            label = "Phone",
            value = phone,
            keyboardType = KeyboardType.Phone,
            onValueChange = { phone = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        EditableTextField(
            label = "City",
            value = city,
            onValueChange = { city = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        EditableTextField(
            label = "Bio",
            value = bio,
            modifier = Modifier.height(100.dp),
            singleLine = false,
            onValueChange = { bio = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        EditableTextField(
            label = "Skills (comma separated)",
            value = skills,
            onValueChange = { skills = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        EditableTextField(
            label = "Interests (comma separated)",
            value = interests,
            onValueChange = { interests = it }
        )

        // Save Button
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onSave(
                    UserProfileRequest(
                        name = name,
                        email = email,
                        city = city,
                        phone = phone,
                        bio = bio,
                        skills = skills.split(",").map { it.trim() },
                        interests = interests.split(",").map { it.trim() }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Save Changes")
            }
        }
    }
}