package com.mobile.volunteerconnect.view.pages.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.volunteerconnect.R

data class Event(
    val title: String,
    val organizer: String,
    val date: String,
    val time: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val upcomingEvents = listOf(
        Event("Community Garden Cleanup", "Green Earth Foundation", "Thu, 15 Jun", "09:00 - 13:00", R.drawable.garden),
        Event("Food Assistance Program", "Mekedonia", "Sat, 24 Jun", "08:00 - 13:00", R.drawable.food)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Volunteer Connect") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.home_icon), contentDescription = "Home") },
                    selected = true,
                    onClick = { /* Navigate to Home if needed */ }
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.explore_icon), contentDescription = "Explore") },
                    selected = false,
                    onClick = { /* Navigate to Explore screen */ }
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.application_icon), contentDescription = "My Applications") },
                    selected = false,
                    onClick = { navController.navigate("applications") } // Navigate to the applications screen
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.profile_icon), contentDescription = "Profile") },
                    selected = false,
                    onClick = { /* Navigate to Profile screen */ }
                )
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp)) {

            item {
                Text("Hello, John! Ready to make a difference today?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Ongoing Event Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Ongoing Event", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Text("MJ Morgan Group Hiring Event at Workforce Development Center",
                            fontSize = 14.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Upcoming Events
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Upcoming Events", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    TextButton(onClick = { navController.navigate("applications") }) {
                        Text("View All")
                    }
                }
            }

            items(upcomingEvents) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = painterResource(id = event.imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(event.title, fontWeight = FontWeight.SemiBold)
                            Text("By ${event.organizer}", fontSize = 12.sp, color = Color.Gray)
                            Text("${event.date}, ${event.time}", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
