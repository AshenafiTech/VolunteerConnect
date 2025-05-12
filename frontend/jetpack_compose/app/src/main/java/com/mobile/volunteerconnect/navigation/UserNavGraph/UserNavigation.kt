package com.mobile.volunteerconnect.navigation.UserNavGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.mobile.volunteerconnect.data.preferences.UserPreferences
import com.mobile.volunteerconnect.view.pages.login.LoginScreen
import com.mobile.volunteerconnect.view.pages.profile.EditProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.ProfileScreen
import com.mobile.volunteerconnect.view.pages.profile.ProfileViewModel
import com.mobile.volunteerconnect.view.user.screens.ApplicationPage
import com.mobile.volunteerconnect.view.user.screens.Explore
import com.mobile.volunteerconnect.view.user.screens.Home
import com.mobile.volunteerconnect.view.user.screens.MyApplication
import kotlinx.coroutines.launch

@Composable
fun UserNavigation() {
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

                listOfNavItems.forEach { navItem ->
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
                                modifier = Modifier.size(24.dp),
                                tint = if (isSelected) SelectedBlue else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                color = if (isSelected) SelectedBlue else MaterialTheme.colorScheme.onPrimaryContainer,
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 10.sp
                            )
                        }
                        ,
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
            startDestination = UserScreens.Home.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(UserScreens.Home.name) { Home() }
//            composable(UserScreens.Explore.name) { Explore() }
            composable(UserScreens.Explore.name) {
                Explore(navController = navController)
            }

            composable(UserScreens.MyApplication.name) { MyApplication() }
            composable(UserScreens.Profile.name) {
                val profileViewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(
                    viewModel   = profileViewModel,
                    onEditClick = { navController.navigate(UserScreens.EditProfile.name) },
                    onDeleted   = { navController.navigate("login") { popUpTo(0) } },
                    onLogout    = { /* clear prefs then navigate to login */
                        coroutineScope.launch {
                            userPreferences.clearUserData()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }}
                )
            }
            composable(UserScreens.EditProfile.name) {
                EditProfileScreen(
                    viewModel = hiltViewModel(),
                    onBack = { navController.popBackStack() },
                    onSaveSuccess = { navController.navigate(UserScreens.Profile.name) { popUpTo(UserScreens.Profile.name){ inclusive=true } } }
                )
            }


            composable(UserScreens.Profile.name) { Profile() }
            composable("ApplicationPage/{eventId}") { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull()
                ApplicationPage(eventId = eventId, onBack = { navController.popBackStack() })
            }



        }
    }
}