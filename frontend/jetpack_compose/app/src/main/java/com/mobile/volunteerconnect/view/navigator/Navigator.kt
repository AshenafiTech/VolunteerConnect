// app/src/main/java/com/mobile/volunteerconnect/view/components/Navigator/Navigator.kt
package com.mobile.volunteerconnect.view.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.volunteerconnect.SplashScreen
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.view.navigator.Screen.CreatePost
import com.mobile.volunteerconnect.view.navigator.Screen.Home
import com.mobile.volunteerconnect.view.navigator.Screen.Login
import com.mobile.volunteerconnect.view.navigator.Screen.Signup
import com.mobile.volunteerconnect.view.pages.createpost.CreatePostScreen
import com.mobile.volunteerconnect.view.pages.homepage.HomeScreen
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.signup.SignupScreen
import com.mobile.volunteerconnect.viewModel.AuthViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object CreatePost : Screen("createPost")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel,
    userPreferences: UserPreferences
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen()
        }

        composable(Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Home.route) {
                        popUpTo(Login.route) { inclusive = true }
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
                        popUpTo(Login.route) { inclusive = true }
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
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

//        composable(Screen.Profile.route) {
//            ProfileScreen(
//                onBackClick = { navController.popBackStack() },
//                onLogout = {
//                    authViewModel.logout()
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(0)
//                    }
//                }
//            )
//        }

        composable(CreatePost.route) {
            CreatePostScreen(
                navController = navController,


            )
        }
    }

}