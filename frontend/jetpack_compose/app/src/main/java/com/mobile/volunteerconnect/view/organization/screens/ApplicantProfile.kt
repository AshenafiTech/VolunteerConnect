package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.volunteerconnect.view.components.TopBarComponent

@Composable
fun ApplicantProfile(userId: String, navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FB)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent("ApplicantProfile: $userId")
            // TODO: Replace with real UI to display applicant details using userId
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicantProfilePreview() {
    val dummyUserId = "123"
    val dummyNavController = rememberNavController()
    ApplicantProfile(userId = dummyUserId, navController = dummyNavController)
}
