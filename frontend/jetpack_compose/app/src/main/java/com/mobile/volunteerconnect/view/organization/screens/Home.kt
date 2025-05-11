package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile.volunteerconnect.view.components.EventCard
import com.mobile.volunteerconnect.view.components.OngoingEventsCarousel
import com.mobile.volunteerconnect.view.components.SimpleSearchBar
import com.mobile.volunteerconnect.view.components.TopBarComponent
import com.mobile.volunteerconnect.view.components.WelcomeText
import com.mobile.volunteerconnect.viewModel.OrgEventsUiState
import com.mobile.volunteerconnect.viewModel.OrgEventsViewModel

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

        is OrgEventsUiState.Error -> {
            val message = (uiState as OrgEventsUiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message, color = MaterialTheme.colorScheme.error)
            }
        }

        is OrgEventsUiState.Success -> {
            val state = uiState as OrgEventsUiState.Success
            val events = state.events

            Column(modifier = Modifier.fillMaxSize()
                 .background(Color( 0xFFF8F9FB))) {

                TopBarComponent("Home")
                SimpleSearchBar(searchQuery = searchQuery, onQueryChange = {
                    searchQuery = it
                    viewModel.onSearchQueryChanged(it)
                })
                WelcomeText(userName = state.userName)


                OngoingEventsCarousel()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Upcoming Events",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(events) { event ->
                        EventCard(event = event)
                    }
                }
            }
        }
    }
}
