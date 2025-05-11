package com.mobile.volunteerconnect.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mobile.volunteerconnect.R
import androidx.compose.foundation.Image

@Composable
fun OngoingEventsCarousel() {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text("Ongoing", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            item {
                OngoingEventCard(
                    text = "Joy and unity in every step at the KindBridge Foundaton even.",
                    imageResId = R.drawable.event_one
                )
            }
            item {
                OngoingEventCard(
                    text = "A single drop of kindness - saving lives together",
                    imageResId = R.drawable.event_two
                )
            }
            item {
                OngoingEventCard(
                    text = "Bringing smiles, building bonds - one moment at a time",
                    imageResId = R.drawable.event_three
                )
            }
        }
    }
}

@Composable
fun OngoingEventCard(text: String, imageResId: Int) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(end = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
