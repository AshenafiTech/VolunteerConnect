package com.mobile.volunteerconnect.navigation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.volunteerconnect.data.model.User
import com.mobile.volunteerconnect.data.preferences.AuthRepository
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.navigation.model.UserRole
import com.mobile.volunteerconnect.navigation.model.UserState
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.signup.SignupScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    authRepository: AuthRepository,
    userPreferences: UserPreferences
) {
    // To track if user is logged in or not
    val userState = remember { mutableStateOf<UserState?>(null) }
    // Track loading state
    val isLoading = remember { mutableStateOf(true) }

    // LaunchedEffect to perform side effects such as checking for token validity
    LaunchedEffect(true) {
        val token = userPreferences.getToken()  // Get the token from preferences

        if (token != null && authRepository.verifyToken()) {
            // Token is valid, user is authenticated
            val userName = userPreferences.getUserName() // Fetch user name
            val userRole = userPreferences.getUserRole() // Fetch user role
            val userEmail = userPreferences.getUserEmail() // Fetch user email

            //  UserState data to be passed
            userState.value = UserState(
                userName ?: "Unknown",
                userEmail ?: "Unknown",
                userRole ?: "Unknown"
            )

            // Set loading to false after the data is loaded
            isLoading.value = false
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            // Token not found or invalid, navigate to login
            isLoading.value = false
            navController.navigate("login")
        }
    }

    // Navigation graph
    NavHost(navController = navController, startDestination = if (isLoading.value) "loading" else if (userState.value != null) "main" else "login") {
        composable("loading") {
            // Show a loading screen while waiting for data
            CircularProgressIndicator()
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // After login success, navigate to the main screen
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
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
                    // After signup success, navigate to the main screen
                    navController.navigate("main") {
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
                val roleEnum = try {
                    UserRole.fromString(user.role)
                } catch (e: IllegalArgumentException) {
                    null
                }

                roleEnum?.let { role ->
                    MainNav(role) // Navigate to the appropriate screen based on user role
                } ?: run {
                    // Handle invalid role error
                    navController.navigate("login")
                }
            } ?: run {
                // Handle null userState error
                navController.navigate("login")
            }
        }
    }
}
