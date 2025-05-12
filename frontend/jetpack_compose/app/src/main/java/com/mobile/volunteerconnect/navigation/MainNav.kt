package com.mobile.volunteerconnect.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mobile.volunteerconnect.navigation.model.UserRole
import com.mobile.volunteerconnect.navigation.OrgNavGraph.OrgNavigation
import com.mobile.volunteerconnect.navigation.UserNavGraph.UserNavigation

@Composable
fun MainNav(userRole: String) {

    val role = UserRole.fromString(userRole)
    when(role) {
        UserRole.Volunteer -> UserNavigation()
        UserRole.Organization -> OrgNavigation()
    }

}
