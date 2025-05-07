package com.mobile.volunteerconnect.navigation.OrgNavGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.mobile.volunteerconnect.view.pages.organization.screens.CreatePost
import com.mobile.volunteerconnect.view.pages.organization.screens.ViewApplicants
import com.example.volunteerconnectjetpackcompose.ui.theme.organization.screens.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobile.volunteerconnect.view.pages.organization.screens.Home
import com.mobile.volunteerconnect.view.pages.organization.screens.UserProfile


data class OrgNavItem(
    val label: String,
    val iconResId: Int,
    val route: String
)

val orgNavItems = listOf(
    OrgNavItem("Home", R.drawable.home_icon, OrgScreens.Home.name),
    OrgNavItem("Create Post", R.drawable.createpost_icon, OrgScreens.CreatePost.name),
    OrgNavItem("Applicants", R.drawable.myapplicationicon, OrgScreens.ViewApplicants.name),
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
            composable(OrgScreens.CreatePost.name) { CreatePost() }
            composable(OrgScreens.ViewApplicants.name) { ViewApplicants() }
            composable(OrgScreens.UserProfile.name) { UserProfile() }
        }
    }
}
