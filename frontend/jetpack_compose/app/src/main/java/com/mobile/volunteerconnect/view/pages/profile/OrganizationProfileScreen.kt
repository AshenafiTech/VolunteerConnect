package com.mobile.volunteerconnect.view.pages.profile

import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import com.mobile.volunteerconnect.data.model.ProfileUser

// Add these imports at the top
import com.mobile.volunteerconnect.view.pages.profile.components.InfoRow
import com.mobile.volunteerconnect.view.pages.profile.components.SkillChip
import com.mobile.volunteerconnect.view.pages.profile.components.StatItem
import kotlin.collections.forEach
import kotlin.collections.isNullOrEmpty

//@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrganizationProfileScreen(
    viewModel: ProfileViewModel,
    onEditClick: () -> Unit,
    onDeleted: () -> Unit,
    onLogout:()-> Unit
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
                    tint = Color.White
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
                    OrganizationProfileContent(
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
fun OrganizationProfileContent(
    user: ProfileUser,
    deleteState: ProfileViewModel.DeleteState,
    onDeleteClicked: () -> Unit,  // Fixed parameter definition
    modifier: Modifier = Modifier,
    onDeleted: () -> Unit , // Added properly
    onLogout: () -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ProfileHead2(user.name ?: "Organization Name")
        Spacer(modifier = Modifier.height(24.dp))
        OrganizationInfoCard(
            user = user,
            deleteState = deleteState,
            onDeleteClicked = onDeleteClicked,
            onDeleted = onDeleted
        )
        Spacer(modifier = Modifier.height(24.dp))
        OrgStatsCard(user)
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
fun ProfileHead2(name: String) {
    Surface(
        tonalElevation = 4.dp,       // for Material3 “surface” elevation
        shadowElevation = 4.dp,      // if you need the legacy shadow API
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Picture",
                tint = Color(0xFF3597DA),
                modifier = Modifier
                    .size(100.dp)
                    .border(2.dp, Color(0xFF3597DA), CircleShape)
            )
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Organization",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}


@Composable
fun OrganizationInfoCard(
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
            InfoRow(Icons.Default.LocationOn, user.city ?: "Location not available")
            InfoRow(Icons.Default.Phone, user.phone ?: "Phone not available")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "About Us",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                user.bio ?: "Organization bio not available.",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
@Composable
fun OrgStatsCard(user: ProfileUser) {
    ElevatedCard(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Stats",
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
                    label = "Events"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OrgDomainsSection(user)
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrgDomainsSection(user: ProfileUser) {
    Column {
        Text("Domains", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        FlowRow(modifier = Modifier.padding(top = 8.dp)) {
            val domains = user.interests
            if (domains.isNullOrEmpty()) {
                SkillChip("Add a domain")
            } else {
                domains.forEach { domain ->
                    SkillChip(domain)
                }
            }
        }
    }
}
