package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobile.volunteerconnect.viewModel.OrgEventsUiState
import com.mobile.volunteerconnect.viewModel.OrgEventsViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Home(viewModel: OrgEventsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    when (uiState) {
        is OrgEventsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is OrgEventsUiState.Success -> {
            val state = uiState as OrgEventsUiState.Success

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Hey, ${state.userName} 👋",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.onSearchQueryChanged(it)
                    },
                    label = { Text("Search events...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true
                )

                LazyColumn {
                    items(state.events) { event ->
                        EventCard(event)
                    }
                }
            }
        }
        is OrgEventsUiState.Error -> {
            val message = (uiState as OrgEventsUiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}


@Composable
fun EventCard(event: com.mobile.volunteerconnect.data.model.EventItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = event.subtitle, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Location: ${event.location}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Date: ${event.date}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Category: ${event.category}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Spots left: ${event.spotsLeft}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
