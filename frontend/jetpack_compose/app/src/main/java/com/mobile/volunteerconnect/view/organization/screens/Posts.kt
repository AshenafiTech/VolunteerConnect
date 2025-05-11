package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobile.volunteerconnect.R
import com.mobile.volunteerconnect.data.model.EventItem
import com.mobile.volunteerconnect.navigation.OrgNavGraph.OrgScreens
import com.mobile.volunteerconnect.view.components.TopBarComponent
import com.mobile.volunteerconnect.viewModel.OrgSpecificEventsViewModel
import kotlin.random.Random

@Composable
fun Posts(
    viewModel: OrgSpecificEventsViewModel = hiltViewModel(),
    navController: NavController // Add NavController as parameter
) {
    val events by viewModel.events.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        viewModel.fetchOrgEvents()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FB)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent("Posts")

            SimpleBar(
                searchQuery = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    viewModel.onSearchQueryChanged(it)
                }
            )

            // Category Filter Chips
            val categories = listOf("All", "Environment", "Education", "Food", "Senior", "Health", "Animals")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        label = category,
                        selected = selectedCategory == category,
                        onClick = {
                            selectedCategory = category
                            viewModel.onCategorySelected(category)
                        }
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) // Show loading spinner
                    }
                    error != null -> {
                        Text(
                            text = error ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    events.isEmpty() -> {
                        Text(
                            text = "No events found.",
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        EventList(
                            events = events,
                            onViewApplicantsClick = { eventId ->
                                // Navigate to the ViewApplicants screen with eventId
                                navController.navigate("view_applicants/$eventId")
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun EventList(events: List<EventItem>, onViewApplicantsClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(events, key = { it.id }) { event ->
            EventCards(event = event, onViewApplicantsClick = onViewApplicantsClick)
        }
    }
}


@Composable
fun EventCards(event: EventItem, onViewApplicantsClick: (Int) -> Unit) {
    val images = listOf(
        R.drawable.event_five,
        R.drawable.event_six,
        R.drawable.event_four
    )
    val randomImage = images[Random.nextInt(images.size)]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = randomImage),
                contentDescription = event.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = event.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF3598db)
            )

            Text(
                text = event.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Place, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = event.location, style = MaterialTheme.typography.bodySmall)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${event.date} ${event.time ?: "08:00 - 11:00"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }


            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${event.spotsLeft} spots left", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onViewApplicantsClick(event.id) },
                modifier = Modifier
                    .widthIn(min = 140.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3598db)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("View Applicants", color = Color.White, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}


@Composable
fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(label) },
        shape = RoundedCornerShape(16.dp),
        border = if (!selected) BorderStroke(1.dp, Color.LightGray) else null,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) Color(0xFF3598db) else Color.White,
            labelColor = if (selected) Color.White else Color.Black,
            leadingIconContentColor = Color.Gray
        )
    )
}

@Composable
fun SimpleBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        placeholder = { Text("Search events...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        ),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
        }
    )
}
