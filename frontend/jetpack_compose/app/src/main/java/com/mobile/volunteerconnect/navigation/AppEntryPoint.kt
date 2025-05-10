package com.mobile.volunteerconnect.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.volunteerconnect.data.model.User
import com.mobile.volunteerconnect.navigation.model.UserRole
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.signup.SignupScreen

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    val userState = remember { mutableStateOf<User?>(null) }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { user ->
                    userState.value = user
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
                onSignupSuccess = { user ->
                    userState.value = user
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("main") {
            userState.value?.let { user ->
                println("User role from backend: ${user.role}")

                val roleEnum = try {
                    UserRole.fromString(user.role)
                } catch (e: IllegalArgumentException) {
                    println("Invalid role received: ${user.role}")
                    null
                }

                roleEnum?.let { role ->
                    println("Mapped UserRole: $role")
                    MainNav(role)
                } ?: run {
                    // Fallback or error handling if role is invalid
                    println("Error: Unable to navigate. Unknown user role.")
                }
            } ?: run {
                println("Error: userState is null at 'main' route.")
            }
        }
    }
}
