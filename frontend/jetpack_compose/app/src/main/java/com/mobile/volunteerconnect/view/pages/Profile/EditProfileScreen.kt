package com.mobile.volunteerconnect.view.pages.Profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxScope.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState = viewModel.uiState
    // Load the user profile when the screen first appears
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }
    // Observe update success
    LaunchedEffect(uiState.updateSuccess) {
        if (uiState.updateSuccess) {
            onSaveSuccess()
            viewModel.resetUpdateSuccess()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
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
                    ProfileForm(
                        profile = uiState.user,
                        onSave = { request ->
                            viewModel.updateUserProfile(request)
                        },
                        isLoading = uiState.isUpdating,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }

                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.resetError() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}