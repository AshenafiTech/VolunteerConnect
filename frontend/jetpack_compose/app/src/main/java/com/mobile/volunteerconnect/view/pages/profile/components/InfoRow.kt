package com.mobile.volunteerconnect.view.pages.profile.components

import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}
//@Composable
//fun InfoRow(icon: ImageVector, text: String) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(vertical = 4.dp)
//    ) {
//        Icon(
//            icon,
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.onSurfaceVariant,
//            modifier = Modifier.size(20.dp)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text)
//    }
//}
