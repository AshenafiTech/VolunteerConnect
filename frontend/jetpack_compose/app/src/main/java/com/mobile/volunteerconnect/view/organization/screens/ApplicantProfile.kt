package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobile.volunteerconnect.view.components.TopBarComponent

@Composable
fun ApplicantProfile(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
        ,
        color = Color(0xFFF8F9FB)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent("ApplicantProfile")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicantProfilePreview(){
    ApplicantProfile()
}
