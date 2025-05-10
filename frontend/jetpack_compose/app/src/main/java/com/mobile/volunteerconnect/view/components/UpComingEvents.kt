package com.mobile.volunteerconnect.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.volunteerconnect.data.model.EventItem
import kotlin.collections.take
import androidx.compose.foundation.lazy.items

@Composable
fun UpcomingEvents(events: List<EventItem>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Upcoming events", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { /* TODO: navigate to all events */ }) {
                Text("View all")
            }
        }

        LazyColumn {
            items(events.take(3)) { event ->
                EventCard(event)
            }
        }
    }
}
