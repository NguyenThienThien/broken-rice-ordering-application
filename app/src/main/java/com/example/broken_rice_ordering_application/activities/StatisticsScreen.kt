package com.example.broken_rice_ordering_application.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen(){
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF252121)),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Statistics Screen",
            fontFamily = FontFamily.Serif,
            fontSize = 22.sp)
    }
}