package com.mobile.volunteerconnect.view.pages.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import com.mobile.volunteerconnect.data.model.ProfileUser


// Add these imports at the top
import com.mobile.volunteerconnect.view.pages.profile.components.ProfileHead
import com.mobile.volunteerconnect.view.pages.profile.components.InfoRow
import com.mobile.volunteerconnect.view.pages.profile.components.SkillChip
import com.mobile.volunteerconnect.view.pages.profile.components.StatItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import kotlin.collections.forEach
import kotlin.run

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onEditClick: () -> Unit,
    onDeleted: () -> Unit,
    onLogout: () -> Unit
) {
    // Call loadUserProfile when the screen first loads
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    val uiState = viewModel.uiState

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                containerColor = Color(0xFF3597DA)

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color.White,
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.user != null -> {
                    ProfileContent(
                        user = uiState.user,
                        deleteState = viewModel.deleteState.collectAsState().value,
                        onDeleteClicked = { viewModel.deleteUserProfile() },
                        onDeleted = onDeleted,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        onLogout = onLogout

                    )
                }

                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
@Composable
private fun ProfileContent(
    user: ProfileUser,
    deleteState: ProfileViewModel.DeleteState,
    onDeleteClicked: () -> Unit,  // Fixed parameter definition
    modifier: Modifier = Modifier,
    onDeleted: () -> Unit,  // Added properly
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        ProfileHead(user.name ?: "")
        Spacer(modifier = Modifier.height(24.dp))
        PersonalInfoCard(
            user = user,
            deleteState = deleteState,
            onDeleteClicked = onDeleteClicked,
            onDeleted = onDeleted
        )
        Spacer(modifier = Modifier.height(24.dp))
        VolunteerStatsCard(user)

        //logut button
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3597DA)
            )

        ) {
            Text("Log Out")
        }

    }
}
@Composable
fun PersonalInfoCard(
    user: ProfileUser,
    deleteState: ProfileViewModel.DeleteState,
    onDeleteClicked: () -> Unit,
    onDeleted: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    ElevatedCard(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Personal Information",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )

                IconButton(
                    onClick = { showDialog.value = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Account")
                }
            }

            // Confirmation AlertDialog
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Confirm Deletion") },
                    text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleteClicked()
                            showDialog.value = false
                        }) {
                            Text("Delete", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }


            when (deleteState) {
                is ProfileViewModel.DeleteState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
                }

                is ProfileViewModel.DeleteState.Error -> {
                    Text(
                        text = deleteState.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                is ProfileViewModel.DeleteState.Success -> {
                    LaunchedEffect(Unit) {
                        onDeleted()
                    }
                }

                else -> {}
            }

            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(Icons.Default.Email, user.email ?: "Not available")
            InfoRow(Icons.Default.LocationOn, user.city ?: "City not available")
            InfoRow(Icons.Default.Phone, user.phone ?: "Phone not available")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Bio", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                user.bio ?: "No bio provided",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Composable
fun VolunteerStatsCard(user: ProfileUser) {
    ElevatedCard(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Volunteer Stats",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatItem(
                    icon = Icons.Default.CalendarToday,
                    value = user.attendedEvents?.toString() ?: "0",
                    label = "Events Attended"
                )
                StatItem(
                    icon = Icons.Default.AccessTime,
                    value = user.hoursVolunteered?.toString() ?: "0",
                    label = "Hours Volunteered"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            SkillsAndInterestsSection(user)
        }
    }
}

@Composable
fun EditableTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,  // Add modifier parameter
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth(),  // Apply the modifier
        singleLine = singleLine,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    )
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillsAndInterestsSection(user: ProfileUser) {
    Column {
        Text("Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        FlowRow(modifier = Modifier.padding(top = 8.dp)) {
            user.skills?.forEach { skill ->
                SkillChip(skill)
            } ?: run {
                SkillChip("Add a skill")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Interests",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        FlowRow(modifier = Modifier.padding(top = 8.dp)) {
            user.interests?.forEach { interest ->
                SkillChip(interest)
            } ?: run {
                SkillChip("Add an interest")
            }
        }
    }
}
