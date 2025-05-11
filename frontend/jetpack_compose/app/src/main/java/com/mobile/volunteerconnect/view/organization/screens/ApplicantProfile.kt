package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.volunteerconnect.viewModel.ApplicantProfileViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ApplicantProfile(
    userId: Int,
    navController: NavController,
    viewModel: ApplicantProfileViewModel = hiltViewModel()
) {
    val userProfile by viewModel.userProfile.collectAsState()

    LaunchedEffect(userId) {
        viewModel.fetchUserProfile(userId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FB)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Top Bar with back button and title
            Row(
                modifier = Modifier.fillMaxWidth().height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF3598db)
                    )
                }

                Text(
                    text = "Applicant Profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                userProfile == null -> {
                    // Loading state
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                userProfile?.user == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error loading profile", color = MaterialTheme.colorScheme.error)
                    }
                }
                else -> {
                    val user = userProfile?.user
                    user?.let {
                        Text(
                            text = "Name: ${it.name}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Email: ${it.email}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Role: ${it.role}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Skills: ${it.skills.joinToString()}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Interests: ${it.interests.joinToString()}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
