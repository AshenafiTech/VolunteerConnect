package com.mobile.volunteerconnect.view.pages.profile.components

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileHead(name: String) {
    Surface(
        tonalElevation = 4.dp,       // for Material3 “surface” elevation
        shadowElevation = 4.dp,      // if you need the legacy shadow API
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Picture",
                tint = Color(0xFF3597DA),
                modifier = Modifier
                    .size(100.dp)
                    .border(2.dp, Color(0xFF3597DA), CircleShape)
            )

            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Volunteer",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }

}

//@Composable
//fun ProfileHead(name: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Icon(
//            imageVector = Icons.Default.AccountCircle,
//            contentDescription = "Profile Picture",
//            tint = MaterialTheme.colorScheme.primary,
//            modifier = Modifier
//                .size(100.dp)
//                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
//        )
//        Text(
//            text = name,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//        Text(
//            text = "Volunteer",
//            fontSize = 14.sp,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//    }
//}