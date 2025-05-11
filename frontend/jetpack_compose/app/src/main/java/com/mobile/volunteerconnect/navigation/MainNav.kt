package com.mobile.volunteerconnect.navigation


import androidx.compose.runtime.Composable
import com.mobile.volunteerconnect.navigation.model.UserRole
import com.mobile.volunteerconnect.navigation.OrgNavGraph.OrgNavigation
import com.mobile.volunteerconnect.navigation.UserNavGraph.UserNavigation

@Composable
fun MainNav(userRole: UserRole) {
    when(userRole) {
        UserRole.Volunteer -> UserNavigation() // Navigate to Volunteer screens
        UserRole.Organization -> OrgNavigation() // Navigate to Organization screens
    }
}
