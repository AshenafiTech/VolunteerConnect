package com.mobile.volunteerconnect.view.pages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SegmentedControl(selectedOption: MutableState<Int>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        listOf("All", "Pending", "Approved", "Others").forEachIndexed { index, label ->
            Button(
                onClick = { selectedOption.value = index },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedOption.value == index) Color(0xFF3598DB) else Color.White
                ),
                modifier = Modifier.padding(end = 2.dp)
            ) {
                Text(
                    text = label,
                    color = if (selectedOption.value == index) Color.White else Color.Black,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun SegmentedControlPreview() {
    val selectedOption = remember { mutableStateOf(0) }
    SegmentedControl(selectedOption)
}
