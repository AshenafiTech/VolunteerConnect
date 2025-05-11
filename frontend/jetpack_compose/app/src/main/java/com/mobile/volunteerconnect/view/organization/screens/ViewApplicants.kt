package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.mobile.volunteerconnect.data.model.ApplicantItem
import com.mobile.volunteerconnect.view.components.ApplicantRow
import com.mobile.volunteerconnect.view.components.ApplicantsSummary
import com.mobile.volunteerconnect.viewModel.ApplicantsViewModel
import androidx.compose.foundation.lazy.items
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ViewApplicants(
    eventId: String,
    navController: NavController,
    viewModel: ApplicantsViewModel = hiltViewModel()
) {
    val applicants by viewModel.applicants.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Trigger fetch when this screen is first composed
    LaunchedEffect(eventId) {
        viewModel.fetchApplicantsByEvent(eventId.toInt())
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Top Bar with back button and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF3598db)
                )
            }

            Text(
                text = "Applicants",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Show Applicants Summary
        ApplicantsSummary(total = applicants.size)

        if (isLoading) {
            // Show loading indicator
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (applicants.isEmpty()) {
            // Show message if no applicants
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No Applicants Yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        } else {
            // Display list of applicants
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(applicants) { applicant ->
                    ApplicantRow(
                        applicant = applicant,
                        onViewClick = {
                            // Navigate to ApplicantProfile screen passing the userId
                            navController.navigate("applicant_profile/${applicant.userId}")
                        }
                    )
                }
            }
        }
    }
}
