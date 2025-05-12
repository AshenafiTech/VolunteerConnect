//package com.mobile.volunteerconnect.view.pages.homepage
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.mobile.volunteerconnect.R
//@Composable
//fun HomeScreen(
//    userPreferences: UserPreferences,
//    navController: NavController,
//    onNavigateToCreatePost: () -> Unit,
//    onNavigateToProfile: () -> Unit
//) {
//    Scaffold(
//        bottomBar = {
//            BottomAppBar {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    IconButton(onClick = { /* Handle home click */ }) {
//                        Icon(Icons.Default.Home, contentDescription = "Home")
//                    }
//                    IconButton(onClick = onNavigateToCreatePost) {
//                        Icon(Icons.Default.Add, contentDescription = "Create Post")
//                    }
//                    IconButton(onClick = onNavigateToProfile) {
//                        Icon(Icons.Default.Person, contentDescription = "Profile")
//                    }
//                }
//            }
//        }
//    ) { padding ->
//        Box(
//            modifier = Modifier
//                .padding(padding)
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(text = "Welcome to VolunteerConnect!")
//                Spacer(modifier = Modifier.height(16.dp))
//                Button(onClick = onNavigateToCreatePost) {
//                    Text("Create Event")
//                }
//            }
//        }
//    }
//}
