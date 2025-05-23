package com.mobile.volunteerconnect.navigation.OrgNavGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import com.mobile.volunteerconnect.R
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.view.organization.screens.ApplicantProfile
import com.mobile.volunteerconnect.view.organization.screens.Home
import com.mobile.volunteerconnect.view.organization.screens.ViewApplicants
import com.mobile.volunteerconnect.view.organization.screens.*
import com.mobile.volunteerconnect.view.pages.createpost.CreatePostScreen
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.profile.EditOrganizationProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.OrganizationProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.ProfileViewModel
import kotlinx.coroutines.launch

data class OrgNavItem(
    val label: String,
    val iconResId: Int,
    val route: String
)

val orgNavItems = listOf(
    OrgNavItem("Home", R.drawable.home_icon, OrgScreens.Home.name),
    OrgNavItem("Create Post", R.drawable.createpost_icon, OrgScreens.CreatePost.name),
    OrgNavItem("Applicants", R.drawable.myapplicationicon, OrgScreens.Posts.name),
    OrgNavItem("Profile", R.drawable.profile_icon, OrgScreens.OrganizationProfile.name)
)




@Composable
fun OrgNavigation() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val coroutineScope = rememberCoroutineScope()

    val navController: NavHostController = rememberNavController()
    val SelectedBlue = Color(0xFF3598DB)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                orgNavItems.forEach { navItem ->
                    val isSelected = currentRoute == navItem.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.iconResId),
                                contentDescription = navItem.label,
                                modifier = Modifier.size(30.dp),
                                tint = if (isSelected) SelectedBlue else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        ,
                        label = {
                            Text(
                                navItem.label,
                                color = if (isSelected) SelectedBlue else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = OrgScreens.Home.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(OrgScreens.Home.name) { Home() }
            composable(OrgScreens.CreatePost.name) { CreatePostScreen(navController) }
//            composable(OrgScreens.ViewApplicants.name) { ViewApplicants(navController) }
//            composable(OrgScreens.ViewApplicants.name) { ViewApplicants() }
//            composable(OrgScreens.UserProfile.name) { UserProfile() }
//            composable("ApplicantProfile/{userId}") { backStackEntry ->
//                val userId = backStackEntry.arguments?.getString("userId")
//                userId?.let {
////                    ApplicantProfile(navController = navController, userId = it, status = status)
////                    ApplicantProfile()
//            composable(OrgScreens.Posts.name) { Posts(navController = navController) } // Pass navController here
//            composable(OrgScreens.Organization.name) { Organization() }
////            composable(OrgScreens.UserProfile.name) { Organization() }

            composable(
                "view_applicants/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId")
                if (eventId != null) {
                    ViewApplicants(eventId = eventId, navController = navController) // Pass the eventId to the screen
                } else {
                    Log.e("Navigation", "Invalid or missing eventId")
                }
            }

            composable(OrgScreens.OrganizationProfile.name) {
                // obtain your Hilt ViewModel
                val profileViewModel: ProfileViewModel = hiltViewModel()
                OrganizationProfileScreen(
                    viewModel = profileViewModel,
                    onEditClick = {
                        navController.navigate(OrgScreens.EditOrganizationProfile.name)
                    },
                    onDeleted = {
                        // after delete, drop back to login
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    },
                    onLogout = {
                        // clear saved prefs & navigate to login
                        coroutineScope.launch {
                            userPreferences.clearUserData()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }

                        }
                    }
                )
            }
            composable(OrgScreens.Posts.name) { Posts(navController = navController) }
            composable(OrgScreens.EditOrganizationProfile.name) {
                EditOrganizationProfileScreen(
                    viewModel = hiltViewModel(),
                    onBack = { navController.popBackStack() },
                    onSaveSuccess = { navController.navigate(OrgScreens.OrganizationProfile.name) {
                        popUpTo(OrgScreens.OrganizationProfile.name) { inclusive = true } } }
                )
            }


            composable(
                "applicant_profile/{userId}/{status}/{eventId}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("status") { type = NavType.StringType } ,
                    navArgument("eventId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId")
                val status = backStackEntry.arguments?.getString("status")
                val eventId = backStackEntry.arguments?.getString("eventId")
                if (userId != null && status != null) {
                    ApplicantProfile(userId = userId, status = status,eventId = eventId.toString(), navController = navController)
                } else {
                    Log.e("Navigation", "Invalid or missing userId or status")
                }
            }



        }
    }
}
