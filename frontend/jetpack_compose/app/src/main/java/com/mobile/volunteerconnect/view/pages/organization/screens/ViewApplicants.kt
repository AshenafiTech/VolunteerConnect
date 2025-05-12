package com.mobile.volunteerconnect.view.pages.organization.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile.volunteerconnect.view.pages.components.ApplicantRow
import com.mobile.volunteerconnect.view.pages.components.ApplicantsSummary
import com.mobile.volunteerconnect.view.pages.components.TopBarComponent
import com.mobile.volunteerconnect.viewModel.ApplicantsViewModel
import com.mobile.volunteerconnect.data.model.ApplicantItem
import androidx.compose.foundation.lazy.items


@Composable
fun ViewApplicants(viewModel: ApplicantsViewModel = hiltViewModel()) {
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(16.dp)
            ) {
                items(applicants) { applicant ->
                    ApplicantRow(applicant)
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(horizontal = 16.dp)

                    )
                }
            }
        }
    }}
}
