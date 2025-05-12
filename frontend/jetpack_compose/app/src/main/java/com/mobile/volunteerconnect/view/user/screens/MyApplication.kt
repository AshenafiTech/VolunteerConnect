package com.mobile.volunteerconnect.view.user.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile.volunteerconnect.view.components.Application
import com.mobile.volunteerconnect.view.components.ApplicationCard
import com.mobile.volunteerconnect.view.components.ApplicationStatus
import com.mobile.volunteerconnect.view.components.SegmentedControl
import com.mobile.volunteerconnect.view.components.TopBarComponent
import com.mobile.volunteerconnect.viewModel.MyApplicationViewModel
import androidx.compose.foundation.lazy.items


@Composable
fun MyApplication(viewModel: MyApplicationViewModel = hiltViewModel()) {
    val selectedOption = remember { mutableStateOf(0) }

    val applicationsResponse = viewModel.state.collectAsState(initial = emptyList())
    val isLoading = viewModel.isLoading.collectAsState(initial = true)

    val applications = applicationsResponse.value.map { response ->
        Application(
            status = ApplicationStatus.fromString(response.status),
            appliedDate = response.appliedAt,
            title = response.title,
            organization = response.subtitle,
            date = response.date,
            time = response.time,
            eventId = response.eventId
        )
    }

    val filteredApplications = when (selectedOption.value) {
        1 -> applications.filter { it.status is ApplicationStatus.Pending }
        2 -> applications.filter { it.status is ApplicationStatus.Approved }
        3 -> applications.filter { it.status is ApplicationStatus.Canceled || it.status is ApplicationStatus.Other }
        else -> applications
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF8F9FB)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent("My Application")
            SegmentedControl(selectedOption)

            if (isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = filteredApplications) { application ->
                        ApplicationCard(
                            application = application,
                            onCancelClick = { eventId ->
                                viewModel.deleteApplication(eventId)
                            }
                        )
                    }
                }
            }
        }
    }
}
