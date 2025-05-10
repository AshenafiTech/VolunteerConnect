package com.mobile.volunteerconnect.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.volunteerconnect.view.pages.homepage.HomeScreen
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color


//@Composable
//fun EventCard(event: Event) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Row(modifier = Modifier.padding(8.dp)) {
//            Box(
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color.LightGray),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("No Image", fontSize = 12.sp, color = Color.DarkGray)
//            }
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            Column {
//                Text(event.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                Text(event.subtitle, fontSize = 14.sp, color = Color.Gray)
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(Icons.Default.Event, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(event.date, fontSize = 12.sp)
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(event.location, fontSize = 12.sp)
//                }
//            }
//        }
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun PreviewHomeScreen() {
//    val sampleEvents = listOf(
//        Event(4, "f8e2...", "Tutor", "Teaching in Addis Ababa University", "Education", "2025-05-04", null, "AAiT", 14, null),
//        Event(3, "a186...", "Jiru Gutema", "rew", "Seniors", "2022-03-03", null, "AAU", 14, null),
//        Event(2, "ae75...", "Meeting", "demo", "Education", "2022-02-02", null, "AA", 14, null)
//    )
//
//    HomeScreen(events = sampleEvents)
//}
