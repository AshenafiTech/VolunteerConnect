package com.mobile.volunteerconnect.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ApplicationCard(
    application: Application,
    onCancelClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = application.status.icon,
                        contentDescription = null,
                        tint = application.status.color,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = application.status.label,
                        color = application.status.color,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Applied on ${application.appliedDate}",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = application.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = application.organization, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("${application.date}   ${application.time}", style = MaterialTheme.typography.bodySmall)
            }

            if (application.status is ApplicationStatus.Pending) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Cancel",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(enabled = onCancelClick != null) {
                            onCancelClick?.invoke()
                        },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApplicationCardPreview() {
    val previewApp = Application(
        status = ApplicationStatus.Pending,
        appliedDate = "Sat, 20 May",
        title = "Community Garden Cleanup",
        organization = "Green Earth Foundation",
        date = "Thu, 15 Jun",
        time = "09:00 - 13:00"
    )
    ApplicationCard(application = previewApp)
}
