package com.mobile.volunteerconnect.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun WelcomeText(userName: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "Hello $userName",
            fontSize = 20.sp,
//            color = Color(0xFF3598DB)
        )
        Text(
            text = "Ready to make a difference today?",
            fontSize = 20.sp
        )
    }
}
