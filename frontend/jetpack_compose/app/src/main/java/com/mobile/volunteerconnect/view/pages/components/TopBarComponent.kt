package com.mobile.volunteerconnect.view.pages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopBarComponent(text:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically



    ) {

        Text( text = text,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

        )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopBarComponentPreview(){
    TopBarComponent("Hey there")
}