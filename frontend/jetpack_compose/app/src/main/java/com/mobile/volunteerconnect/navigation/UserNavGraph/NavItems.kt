package com.mobile.volunteerconnect.navigation.UserNavGraph


import com.mobile.volunteerconnect.R
import com.mobile.volunteerconnect.navigation.OrgNavGraph.OrgScreens

data class NavItem(
    val label: String,
    val iconResId: Int,
    val route: String
)

val listOfNavItems: List<NavItem> = listOf(
    NavItem(
        label = "Home",
        iconResId = R.drawable.home_icon,
        route = UserScreens.Home.name
    ),
    NavItem(
        label = "Explore",
        iconResId = R.drawable.explore_icon,
        route = UserScreens.Explore.name
    ),
    NavItem(
        label = "My Applications",
        iconResId = R.drawable.myapplicationicon,
        route = UserScreens.MyApplication.name
    ),

    NavItem(
        label = "Profile",
        iconResId = R.drawable.profile_icon,
        route = UserScreens.Profile.name
    ))
