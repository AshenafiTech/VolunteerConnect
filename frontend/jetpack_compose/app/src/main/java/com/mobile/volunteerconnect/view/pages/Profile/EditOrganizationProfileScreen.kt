package com.mobile.volunteerconnect.view.pages.Profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOrganizationProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    LaunchedEffect(uiState.updateSuccess) {
        if (uiState.updateSuccess) {
            onSaveSuccess()
            viewModel.resetUpdateSuccess()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Organization Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                uiState.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                uiState.user != null -> OrganizationProfileForm(
                    profile = uiState.user,
                    onSave = { request ->
                        viewModel.updateUserProfile(request)
                    },
                    isLoading = uiState.isUpdating,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

                uiState.error != null ->
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

