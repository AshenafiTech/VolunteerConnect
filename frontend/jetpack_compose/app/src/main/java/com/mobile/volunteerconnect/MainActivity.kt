package com.mobile.volunteerconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mobile.volunteerconnect.data.preferences.AuthRepository
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.navigation.AppNavHost
import com.mobile.volunteerconnect.ui.theme.VolunteerConnectTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent creates the composable scope
        setContent {
            // Create navController inside setContent (composable scope)
            val navController = rememberNavController()

            // Call AppNavHost in the composable context
            VolunteerConnectTheme {
                AppNavHost(
                    navController = navController,  // Pass the navController here
                    authRepository = authRepository,
                    userPreferences = userPreferences
                )
            }
        }
    }
}


//
//@Composable
//fun AppNavigation(userPreferences: UserPreferences) {
//    val navController = rememberNavController()
//    val viewModel: AuthViewModel = hiltViewModel()
//    val isTokenValid = viewModel.isTokenValid
//
//    if (isTokenValid == null) {
//        SplashScreen()
//    } else {
//        NavHost(
//            navController = navController,
//            startDestination = if (isTokenValid) Home.route else Login.route
//        ) {
//            composable(Login.route) {
//                LoginScreen(
//                    onLoginSuccess = {
//                        navController.navigate(Home.route) {
////                            popUpTo(Login.route) { NavOptionsBuilder.inclusive = true }
//                        }
//                    },
//                    onSignUpClick = {
//                        navController.navigate(Signup.route)
//                    }
//                )
//            }
//
//            composable(Signup.route) {
//                SignupScreen(
//                    onSignupSuccess = {
//                        navController.navigate(Home.route) {
////                            popUpTo(Login.route) { NavOptionsBuilder.inclusive = true }
//                        }
//                    },
//                    onLoginClick = {
//                        navController.navigate(Login.route)
//                    }
//                )
//            }
//
//            composable(Home.route) {
//                HomeScreen(
//                    userPreferences = userPreferences,
//                    navController = navController,
//                    onNavigateToCreatePost = {
//                        navController.navigate(CreatePost.route)
//                    },
//                    onNavigateToProfile = {
//
//                        navController.navigate("profile")
//                    }
//                )
//            }
//
//            composable(CreatePost.route) {
//                CreatePostScreen(navController = navController)
//            }
//        }
//    }
//}

//@Composable
//fun SplashScreen() {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        CircularProgressIndicator()
//    }}
