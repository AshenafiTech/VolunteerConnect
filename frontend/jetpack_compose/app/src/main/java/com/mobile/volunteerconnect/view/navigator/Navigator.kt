// app/src/main/java/com/mobile/volunteerconnect/view/components/Navigator/Navigator.kt
package com.mobile.volunteerconnect.view.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.mobile.volunteerconnect.util.decodeJwtPayload
import com.mobile.volunteerconnect.view.navigator.Screen.CreatePost
import com.mobile.volunteerconnect.view.navigator.Screen.Home
import com.mobile.volunteerconnect.view.navigator.Screen.Login
import com.mobile.volunteerconnect.view.navigator.Screen.Signup
import com.mobile.volunteerconnect.view.pages.createpost.CreatePostScreen
import com.mobile.volunteerconnect.view.pages.homepage.HomeScreen
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.profile.EditOrganizationProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.EditProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.OrganizationProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.ProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.ProfileViewModel
import com.mobile.volunteerconnect.view.pages.signup.SignupScreen
import com.mobile.volunteerconnect.viewModel.AuthViewModel
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object CreatePost : Screen("createPost")
    object OrganizationProfile : Screen("organizationProfile")
    object EditProfile : Screen("editProfile")
    object EditOrganizationProfile : Screen("editOrganizationProfile")
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
        composable(Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            val coroutineScope = rememberCoroutineScope()
            var userRole by remember { mutableStateOf<String?>(null) }
            var loading  by remember { mutableStateOf(true) }
            var error    by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                try {
                    // 1. Load raw JWT
                    val rawToken = userPreferences.getToken()
                        ?: throw IllegalStateException("No token found")

                    // 2. Decode payload
                    val payload = decodeJwtPayload(rawToken)
                        ?: throw IllegalStateException("Bad JWT payload")

                    // 3. Safely extract the role claim
                    //    Adjust the key to whatever your JWT actually uses
                    userRole = payload.optString("role", "")
                } catch (e: Exception) {
                    error = e.message
                } finally {
                    loading = false
                }
            }

            when {
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: $error")
                    }
                }
                userRole.equals("organization", ignoreCase = true) -> {
                    OrganizationProfileScreen(
                        viewModel   = profileViewModel,
                        onEditClick = {
                            navController.navigate(Screen.EditOrganizationProfile.route)
                        },
                        onDeleted   = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0)
                            }
                        },
                        onLogout    = {
                            coroutineScope.launch {
                                userPreferences.clearUserData()
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0)
                                }
                            }
                        }
                    )
                }
                else -> {
                    ProfileScreen(
                        viewModel   = profileViewModel,
                        onEditClick = {
                            navController.navigate(Screen.EditProfile.route)
                        },
                        onDeleted   = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0)
                            }
                        },
                        onLogout    = {
                            coroutineScope.launch {
                                userPreferences.clearUserData()
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0)
                                }
                            }
                        }
                    )
                }
            }
        }

        composable(Screen.EditProfile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            EditProfileScreen(
                viewModel = profileViewModel,
                onBack = { navController.popBackStack() },
                onSaveSuccess = {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Profile.route){inclusive=true}
                    }
                }
            )
        }
        composable(Screen.OrganizationProfile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            val coroutineScope = rememberCoroutineScope()

            OrganizationProfileScreen(
                viewModel   = profileViewModel,
                onEditClick = { navController.navigate(Screen.EditOrganizationProfile.route) },
                onDeleted   = {
                    navController.navigate(Screen.Login.route) { popUpTo(0) }
                },
                onLogout    = {
                    coroutineScope.launch {
                        userPreferences.clearUserData()
                        navController.navigate(Screen.Login.route) { popUpTo(0) }
                    }
                }
            )
        }

        composable(Screen.EditOrganizationProfile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            EditOrganizationProfileScreen(
                viewModel = profileViewModel,
                onBack = { navController.popBackStack() },
                onSaveSuccess = {
                    navController.navigate(Screen.OrganizationProfile.route) {
                        popUpTo(Screen.OrganizationProfile.route) { inclusive = true }
                    }
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