package com.mobile.volunteerconnect.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.volunteerconnect.data.preferences.AuthRepository
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.navigation.model.UserRole
import com.mobile.volunteerconnect.navigation.model.UserState
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.signup.SignupScreen
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    authRepository: AuthRepository = hiltViewModel(),
    userPreferences: UserPreferences = hiltViewModel()
) {
    val userState = remember { mutableStateOf<UserState?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorState = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Handle initial auth check
    LaunchedEffect(Unit) {
        try {
            withTimeout(30_000) {
                val token = userPreferences.getToken()
                android.util.Log.d("AuthFlow", "Checking auth state. Token exists: ${token != null}")

                if (token != null) {
                    val isValid = authRepository.verifyToken()
                    android.util.Log.d("AuthFlow", "Token verification result: $isValid")

                    if (isValid) {
                        val userName = userPreferences.getUserName() ?: "Unknown"
                        val userRole = userPreferences.getUserRole() ?: "Unknown"
                        val userEmail = userPreferences.getUserEmail() ?: "Unknown"

                        userState.value = UserState(userName, userEmail, userRole)
                        android.util.Log.d("AuthFlow", "User authenticated: $userName")
                    } else {
                        android.util.Log.w("AuthFlow", "Invalid token, clearing data")
                        userPreferences.clearUserData()
                    }
                }
            }
        } catch (e: Exception) {
            errorState.value = "Authentication check failed: ${e.message}"
            android.util.Log.e("AuthFlow", "Auth check error", e)
        } finally {
            isLoading.value = false
        }
    }

    // Show loading/error state if needed
    if (isLoading.value) {
        LoadingScreen(errorState.value)
        return
    }


    // Main navigation graph
    NavHost(
        navController = navController,
        startDestination = when {
            userState.value != null -> "main"
            else -> "login"
        }
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    scope.launch {
                        try {
                            // Get fresh user data after login
                            val userName = userPreferences.getUserName() ?: "Unknown"
                            val userRole = userPreferences.getUserRole() ?: "Unknown"
                            val userEmail = userPreferences.getUserEmail() ?: "Unknown"
                            userState.value = UserState(userName, userEmail, userRole)

                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            errorState.value = "Navigation failed: ${e.message}"
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }

                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }

        composable("main") {
            userState.value?.let { user ->
                when (val role = try {
                    UserRole.fromString(user.role)
                } catch (e: IllegalArgumentException) {
                    null
                }) {
                    null -> {
                        LaunchedEffect(Unit) {
                            errorState.value = "Invalid user role: ${user.role}"
                            navController.navigate("login") { popUpTo(0) }
                        }
                        Box {} // Empty composable while navigating
                    }
                    else -> MainNav(userRole = user.role.toString())
                }
            } ?: run {
                LaunchedEffect(Unit) {
                    navController.navigate("login") { popUpTo(0) }
                }
                Box {} // Empty composable while navigating
            }
        }
    }
}

@Composable
private fun LoadingScreen(error: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
