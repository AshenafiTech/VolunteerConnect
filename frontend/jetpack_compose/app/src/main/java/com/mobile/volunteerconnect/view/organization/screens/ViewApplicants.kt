package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobile.volunteerconnect.view.components.ApplicantRow
import com.mobile.volunteerconnect.view.components.ApplicantsSummary
import com.mobile.volunteerconnect.view.components.TopBarComponent
import com.mobile.volunteerconnect.viewModel.ApplicantsViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun ViewApplicants(
    navController: NavController,
    viewModel: ApplicantsViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val applicants by viewModel.applicants.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB))
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                TopBarComponent("Applicants List")

                ApplicantsSummary(applicants.size)

                if (applicants.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No applicants yet",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(applicants) { applicant ->
                            ApplicantRow(applicant = applicant, navController = navController)
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = Color.LightGray,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
