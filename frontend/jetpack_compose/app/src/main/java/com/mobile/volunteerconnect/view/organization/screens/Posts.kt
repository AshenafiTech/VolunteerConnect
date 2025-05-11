package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile.volunteerconnect.data.model.EventItem
import com.mobile.volunteerconnect.view.components.TopBarComponent
import com.mobile.volunteerconnect.viewModel.OrgSpecificEventsViewModel
import kotlin.random.Random
import com.mobile.volunteerconnect.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search


@Composable
fun Posts(
    viewModel: OrgSpecificEventsViewModel = hiltViewModel()
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

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val categories = listOf("All", "Environment", "Education", "Food", "Senior", "Health", "Animals")

                items(categories.size) { index ->
                    val category = categories[index]
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
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    error != null -> {
                        Text(
                            text = error ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        EventList(events)
                    }
                }
            }
        }
    }
}



@Composable
fun EventList(events: List<EventItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(events, key = { it.id }) { event ->
            EventCards(event)
        }
    }
}

@Composable
fun EventCards(event: EventItem) {
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
            // Image
            Image(
                painter = painterResource(id = randomImage),
                contentDescription = event.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF3598db)
            )

            // Subtitle
            Text(
                text = event.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date and Time
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = event.date, style = MaterialTheme.typography.bodySmall)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Time",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.time ?: "09:00 - 12:00",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Location",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = event.location, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Available Spots
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Available Spots",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Available Spots: ${event.spotsLeft}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // View Applicants Button
            Button(
                onClick = { /* Handle action */ },
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
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        }
    )
}
