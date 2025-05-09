package com.mobile.volunteerconnect.view.pages.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.volunteerconnect.R

// Define the Event class
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
    val ongoingEvents = listOf(
        Event("MJ Morgan Group Hiring Event", "Workforce Development Center", "Today", "09:00 - 13:00", R.drawable.garden) // Ensure 'garden' image is in 'res/drawable'
    )

    val upcomingEvents = listOf(
        Event("Community Garden Cleanup", "Green Earth Foundation", "Thu, 15 Jun", "09:00 - 13:00", R.drawable.garden), // Ensure 'garden' image is in 'res/drawable'
        Event("Food Assistance Program", "Mekedonia", "Sat, 24 Jun", "08:00 - 13:00", R.drawable.food) // Ensure 'food' image is in 'res/drawable'
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Home", modifier = Modifier.padding(start = 16.dp)) // Title on the left
                },
                actions = {
                    IconButton(onClick = { /* Handle logo click here if needed */ }) {
                        Icon(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo") // Ensure 'logo' image is in 'res/drawable'
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.home_icon), contentDescription = "Home") }, // Ensure 'home_icon' is in 'res/drawable'
                    selected = true,
                    onClick = { /* Stay on the home screen */ }
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.explore_icon), contentDescription = "Explore") }, // Ensure 'explore_icon' is in 'res/drawable'
                    selected = false,
                    onClick = { navController.navigate("explore") }
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.application_icon), contentDescription = "My Applications") }, // Ensure 'application_icon' is in 'res/drawable'
                    selected = false,
                    onClick = { navController.navigate("applications") }
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.profile_icon), contentDescription = "Profile") }, // Ensure 'profile_icon' is in 'res/drawable'
                    selected = false,
                    onClick = { navController.navigate("profile") }
                )
            }
        }
    ) { padding ->

        LazyColumn(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp)) {

            // Greeting Section
            item {
                Text("Hello, John! Ready to make a difference today?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Search Bar Section
            item {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                    placeholder = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp)), // Rounded corners here
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Ongoing Event Section
            item {
                Text("Ongoing", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(ongoingEvents[0].title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Text("Location: ${ongoingEvents[0].organizer}", fontSize = 14.sp, color = Color.Gray)
                        Text("${ongoingEvents[0].date} | ${ongoingEvents[0].time}", fontSize = 12.sp, color = Color.Gray)
                    }
                    Image(
                        painter = painterResource(id = ongoingEvents[0].imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.5f }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Upcoming Events Section
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

            // List of upcoming events using items
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
