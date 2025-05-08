package com.mobile.volunteerconnect.view.pages.createpost

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobile.volunteerconnect.R


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Create New Post",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.shadow(elevation = 4.dp) // Adds a bottom shadow
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Create Post",

                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Fill in the details for your volunteer Activity",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Event Title
            Text(
                text = "Event Title",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.title,
                onValueChange = viewModel::updateTitle,
                placeholder = { Text("Enter event title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            // Event Title
            Text(
                text = "Sub Title",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.subtitle,
                onValueChange = viewModel::updateSubtitle,
                placeholder = { Text("Enter event Subtitle") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Description
            Text(
                text = "Description",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::updateDescription,
                placeholder = { Text("Describe the volunteer opportunity") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Location
            Text(
                text = "Location",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.location,
                onValueChange = viewModel::updateLocation,
                placeholder = { Text("Enter event location") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date
            Text(
                text = "Date",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = uiState.date.split("-").getOrElse(2) { "" },
                    onValueChange = { day ->
                        val parts = uiState.date.split("-")
                        val newDate = "${parts.getOrElse(0) { "" }}-${parts.getOrElse(1) { "" }}-$day"
                        viewModel.updateDate(newDate)
                    },
                    placeholder = { Text("Day") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface
                    )
                )
                OutlinedTextField(
                    value = uiState.date.split("-").getOrElse(1) { "" },
                    onValueChange = { month ->
                        val parts = uiState.date.split("-")
                        val newDate = "${parts.getOrElse(0) { "" }}-$month-${parts.getOrElse(2) { "" }}"
                        viewModel.updateDate(newDate)
                    },
                    placeholder = { Text("Month") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface
                    )
                )
                OutlinedTextField(
                    value = uiState.date.split("-").getOrElse(0) { "" },
                    onValueChange = { year ->
                        val parts = uiState.date.split("-")
                        val newDate = "$year-${parts.getOrElse(1) { "" }}-${parts.getOrElse(2) { "" }}"
                        viewModel.updateDate(newDate)
                    },
                    placeholder = { Text("Year") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Time
            Text(
                text = "Time",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = uiState.time.split(":").getOrElse(0) { "" },
                    onValueChange = { hours ->
                        val parts = uiState.time.split(":")
                        val newTime = "$hours:${parts.getOrElse(1) { "" }}"
                        viewModel.updateTime(newTime)
                    },
                    placeholder = { Text("Start") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface
                    )
                )
                OutlinedTextField(
                    value = uiState.time.split(":").getOrElse(1) { "" },
                    onValueChange = { minutes ->
                        val parts = uiState.time.split(":")
                        val newTime = "${parts.getOrElse(0) { "" }}:$minutes"
                        viewModel.updateTime(newTime)
                    },
                    placeholder = { Text("End") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Category
            Text(
                text = "Category",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Seniors", "Community", "Education", "Animals", "Environment", "Health").forEach { category ->
                    FilterChip(
                        selected = uiState.category == category,
                        onClick = { viewModel.updateCategory(category) },
                        label = { Text(category) },
                        modifier = Modifier.padding(end = 4.dp),
                        shape = MaterialTheme.shapes.small
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Image URL
            Text(
                text = "Image URL",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.image,
                onValueChange = viewModel::updateImage,
                placeholder = { Text("Enter image URL") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            // Image URL
            Text(
                text = "Spot left",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.spotsLeft,
                onValueChange = viewModel::updateSpotsLeft,
                placeholder = { Text("Spot left") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Event Title
            Text(
                text = "Additional Info",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.additionalInfo,
                onValueChange = viewModel::updateAdditionalInfo,
                placeholder = { Text("Enter additional information") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Event Title
            Text(
                text = "Requirement",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.requirements.joinToString(", "),
                onValueChange = { input ->
                    val updatedList = input.split(",").map { it.trim() }
                    viewModel.updateRequirements(updatedList)
                },
                placeholder = { Text("Enter requirement information (comma-separated)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Event Title
            Text(
                text = "Email Info",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.contactEmail,
                onValueChange = viewModel::updateContactEmail,
                placeholder = { Text("Enter Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Event Title
            Text(
                text = "Phone Info",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.contactPhone,
                onValueChange = viewModel::updateContactPhone,
                placeholder = { Text("Enter Phone") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Event Title
            Text(
                text = "Telegram Info",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = uiState.contactTelegram,
                onValueChange = viewModel::updateContactTelegram,
                placeholder = { Text("Enter Telegram handler") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = viewModel::createEvent,
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading &&
                            uiState.title.isNotEmpty() &&
                            uiState.description.isNotEmpty() &&
                            uiState.location.isNotEmpty() &&
                            uiState.date.isNotEmpty() &&
                            uiState.time.isNotEmpty() &&
                            uiState.category.isNotEmpty(),
                    shape = MaterialTheme.shapes.small
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = colors.onPrimary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Create Event")
                    }
                }
            }

            uiState.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = colors.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }


        }

    }
    }
