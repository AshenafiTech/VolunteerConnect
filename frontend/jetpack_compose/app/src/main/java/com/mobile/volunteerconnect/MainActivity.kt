package com.mobile.volunteerconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.ui.theme.VolunteerConnectTheme
import com.mobile.volunteerconnect.view.navigator.Screen.CreatePost
import com.mobile.volunteerconnect.view.navigator.Screen.Home
import com.mobile.volunteerconnect.view.navigator.Screen.Login
import com.mobile.volunteerconnect.view.navigator.Screen.Signup
import com.mobile.volunteerconnect.view.pages.createpost.CreatePostScreen
import com.mobile.volunteerconnect.view.pages.homepage.HomeScreen
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.signup.SignupScreen
import com.mobile.volunteerconnect.viewModel.AuthViewModel

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VolunteerConnectTheme {
                AppNavigation(userPreferences)
            }
        }
    }
}




@Composable
fun AppNavigation(userPreferences: UserPreferences) {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = hiltViewModel()
    val isTokenValid = viewModel.isTokenValid

    if (isTokenValid == null) {
        SplashScreen()
    } else {
        NavHost(
            navController = navController,
            startDestination = if (isTokenValid) Login.route else Home.route
        ) {
            composable(Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Home.route) {
//                            popUpTo(Login.route) { NavOptionsBuilder.inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(Signup.route)
                    }
                )
            }

            composable(Signup.route) {
                SignupScreen(
                    onSignupSuccess = {
                        navController.navigate(Home.route) {
//                            popUpTo(Login.route) { NavOptionsBuilder.inclusive = true }
                        }
                    },
                    onLoginClick = {
                        navController.navigate(Login.route)
                    }
                )
            }

            composable(Home.route) {
                HomeScreen(
                    userPreferences = userPreferences,
                    navController = navController,
                    onNavigateToCreatePost = {
                        navController.navigate(CreatePost.route)
                    },
                    onNavigateToProfile = {

                        navController.navigate("profile")
                    }
                )
            }

            composable(CreatePost.route) {
                CreatePostScreen(navController = navController)
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }}
