package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobile.volunteerconnect.data.model.ApplicantItem
import com.mobile.volunteerconnect.viewModel.ApplicantsViewModel
import androidx.compose.foundation.lazy.items
import com.mobile.volunteerconnect.view.components.ApplicantRow
import com.mobile.volunteerconnect.view.components.ApplicantsSummary


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
                    ApplicantRow(applicant = applicant)
                }
            }
        }
    }
}
