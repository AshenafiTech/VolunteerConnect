package com.mobile.volunteerconnect.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


sealed class ApplicationStatus(
    val label: String,
    val color: Color,
    val icon: ImageVector
) {
    object Approved : ApplicationStatus("Approved", Color(0xFF14DA6F), Icons.Filled.CheckCircle)
    object Pending : ApplicationStatus("Pending", Color(0xFFF49924), Icons.Filled.DateRange)
    object Canceled : ApplicationStatus("Canceled", Color.Gray, Icons.Filled.Close)
    object Other : ApplicationStatus("Other", Color.Black, Icons.Filled.Info)

    companion object {
        fun fromString(value: String): ApplicationStatus {
            return when (value) {
                "Approved" -> Approved
                "Pending" -> Pending
                "Canceled" -> Canceled
                else -> Other
            }
        }
    }
}
