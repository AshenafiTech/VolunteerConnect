package com.mobile.volunteerconnect.view.user.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mobile.volunteerconnect.view.components.TopBarComponent

@Composable
fun Home(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
        ,
        color = Color(0xFFF8F9FB)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent("Home")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    Home()
}
