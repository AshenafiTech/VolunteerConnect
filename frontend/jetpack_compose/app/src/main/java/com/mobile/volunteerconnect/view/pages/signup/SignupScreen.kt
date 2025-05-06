package com.mobile.volunteerconnect.view.pages.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile.volunteerconnect.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    // Handle success
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onSignupSuccess()
        }
    }

    // Handle errors
    if (uiState.error != null) {
        LaunchedEffect(uiState.error) {
            // Show error toast or snackbar
            viewModel.resetError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Create Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Volunteer. Connect. Impact.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()


        )

        Spacer(Modifier.height(32.dp))

        // Name Field
        Text(
            text = "Full name",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.name,
            onValueChange = viewModel::onNameChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Email Field
        Text(
            text = "Email",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Password Field
        Text(
            text = "Password",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Role Selection
        Text(
            text = "Role",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = uiState.role == "Volunteer",
                    onClick = { viewModel.onRoleChange("Volunteer") }
                )
                Text(
                    text = "Volunteer",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }


            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = uiState.role == "Organization",
                    onClick = { viewModel.onRoleChange("Organization") }
                )
                Text(
                    text = "Organization",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Sign Up Button
        Button(
            onClick = viewModel::signup,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3597DA)),
            shape = MaterialTheme.shapes.small
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Sign up")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Login Link
        TextButton(
            onClick = onLoginClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Have an account? Login")
        }
    }
}