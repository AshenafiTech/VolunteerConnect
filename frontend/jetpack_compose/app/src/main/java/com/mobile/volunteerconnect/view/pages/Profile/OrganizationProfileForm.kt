package com.mobile.volunteerconnect.view.pages.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun OrganizationProfileForm(
    profile: ProfileUser,
    onSave: (UserProfileRequest) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(profile.name ?: "") }
    var email by remember { mutableStateOf(profile.email ?: "") }
    var phone by remember { mutableStateOf(profile.phone ?: "") }
    var city by remember { mutableStateOf(profile.city ?: "") }
    var bio by remember { mutableStateOf(profile.bio ?: "") }
    var domains by remember { mutableStateOf(profile.interests?.joinToString(", ") ?: "") }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Organization Info", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))

        EditableTextField("Organization Name", name) { name = it }
        Spacer(Modifier.height(8.dp))
        EditableTextField("Email", email, keyboardType = KeyboardType.Email) { email = it }
        Spacer(Modifier.height(8.dp))
        EditableTextField("Phone", phone, keyboardType = KeyboardType.Phone) { phone = it }
        Spacer(Modifier.height(8.dp))
        EditableTextField("City", city) { city = it }
        Spacer(Modifier.height(8.dp))
        EditableTextField(
            "About Us",
            bio,
            modifier = Modifier.height(100.dp),
            singleLine = false
        ) { bio = it }
        Spacer(Modifier.height(8.dp))
        EditableTextField("Domains (comma separated)", domains) { domains = it }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                onSave(
                    UserProfileRequest(
                        name = name,
                        email = email,
                        city = city,
                        phone = phone,
                        bio = bio,
                        skills = emptyList(), // or null if your backend allows
                        interests = domains.split(",").map { it.trim() }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Save Changes")
            }
        }
    }
}
