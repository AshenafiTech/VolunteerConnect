package com.mobile.volunteerconnect.navigation.OrgNavGraph

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.mobile.volunteerconnect.R
import com.mobile.volunteerconnect.view.organization.screens.*
import com.mobile.volunteerconnect.view.pages.createpost.CreatePostScreen

data class OrgNavItem(
    val label: String,
    val iconResId: Int,
    val route: String
)

val orgNavItems = listOf(
    OrgNavItem("Home", R.drawable.home_icon, OrgScreens.Home.name),
    OrgNavItem("Create Post", R.drawable.createpost_icon, OrgScreens.CreatePost.name),
    OrgNavItem("Posts", R.drawable.compass, OrgScreens.Posts.name),
    OrgNavItem("Profile", R.drawable.profile_icon, OrgScreens.UserProfile.name)
)

@Composable
fun OrgNavigation() {
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
                        },
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
            composable(OrgScreens.Posts.name) { Posts(navController = navController) } // Pass navController here
            composable(OrgScreens.Organization.name) { Organization() }
            composable(OrgScreens.UserProfile.name) { Organization() }

            // Add the new route for ViewApplicantsScreen with eventId argument
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
        }
    }
}
