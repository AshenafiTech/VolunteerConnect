package com.example.volunteerconnectjetpackcompose.ui.theme.user.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mobile.volunteerconnect.view.pages.components.TopBarComponent

@Composable
fun Profile(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
        ,
        color = Color(0xFFF8F9FB)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent("Profile")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    Profile()
}
