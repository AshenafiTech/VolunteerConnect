package com.mobile.volunteerconnect.navigation.UserNavGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.mobile.volunteerconnect.view.user.screens.Explore
import com.mobile.volunteerconnect.view.user.screens.Home
import com.mobile.volunteerconnect.view.user.screens.MyApplication
import com.mobile.volunteerconnect.view.user.screens.Profile

@Composable
fun UserNavigation() {
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
            composable(UserScreens.Explore.name) { Explore() }
            composable(UserScreens.MyApplication.name) { MyApplication() }
            composable(UserScreens.Profile.name) { Profile() }
        }
    }
}