package com.mobile.volunteerconnect.view.pages.homepage

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import kotlinx.coroutines.launch
// HomeScreen.kt
@Composable
fun HomeScreen(
    userPreferences: UserPreferences,
    navController: NavController,
    onNavigateToCreatePost: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { /* Handle home click */ }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = onNavigateToCreatePost) {
                        Icon(Icons.Default.Add, contentDescription = "Create Post")
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Welcome to VolunteerConnect!")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateToCreatePost) {
                    Text("Create Event")
                }
            }
        }
    }
}