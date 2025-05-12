package com.mobile.volunteerconnect.view.user.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile.volunteerconnect.viewModel.EventDetailViewModel
import com.mobile.volunteerconnect.R
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationPage(
    eventId: Int? = null,
    onBack: () -> Unit,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    // Trigger event loading when the eventId is provided
    LaunchedEffect(eventId) {
        eventId?.let { viewModel.loadEvent(it) }
    }

    val drawableOptions = listOf(
        R.drawable.event_one,
        R.drawable.event_three,
        R.drawable.event_four,
        R.drawable.event_five,
        R.drawable.event_six
    )
    val randomImage = remember { drawableOptions.random() }

    val primaryBlue = Color(0xFF3598DB)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Application Page", fontWeight = FontWeight.Bold ) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = primaryBlue)
                    }
                }
            )
        },
        bottomBar = {
            if (viewModel.event.value != null) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            eventId?.let { viewModel.applyToEvent(it) }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                    ) {
                        Text("Apply", color = Color.White)
                    }
                }
            }
        }
    ) { padding ->
        when {
            viewModel.isLoading.value -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            viewModel.errorMessage.value != null -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${viewModel.errorMessage.value}", color = Color.Red)
                }
            }

            viewModel.event.value != null -> {
                val event = viewModel.event.value!!

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    item {
                        Image(
                            painter = painterResource(id = randomImage),
                            contentDescription = "Event Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            // Category
                            event.category?.let {
                                Text(
                                    text = it,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .background(primaryBlue, shape = MaterialTheme.shapes.small)
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            // Title and Subtitle
                            Text(
                                event.title,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            event.subtitle?.let {
                                Text(
                                    it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            // Application Status
                            viewModel.applyStatus?.let {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = it,
                                    color = if (it.contains("Error") || it.contains("already applied")) Color.Red else Color.Green,
                                    fontWeight = FontWeight.Bold
                                )
                            }


                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = Color.LightGray)

                            // Description
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "Description",
                                style = MaterialTheme.typography.titleMedium,
                                color = primaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                            Text(event.description ?: "No description provided.")


                            // Requirements
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Requirements", style = MaterialTheme.typography.titleMedium, color = primaryBlue)
                            Text(event.requirements ?: "Not specified.")

                            // Location
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Location", style = MaterialTheme.typography.titleMedium, color = primaryBlue)
                            Text(event.location ?: "Not specified.")

                            // Date and Time
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Date & Time", style = MaterialTheme.typography.titleMedium, color = primaryBlue)
                            Text("Date: ${event.date ?: "N/A"}")
                            Text("Time: ${event.time ?: "N/A"}")

                            // Additional Info
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Additional Info", style = MaterialTheme.typography.titleMedium, color = primaryBlue)
                            Text(event.additionalInfo ?: "None")

                            // Contact Info
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Contact Info", style = MaterialTheme.typography.titleMedium, color = primaryBlue)
                            event.contactPhone?.let { Text(" Phone: $it") }
                            event.contactEmail?.let { Text("Email: $it") }
                            event.contactTelegram?.let { Text("Telegram: $it") }

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}
