package com.example.broken_rice_ordering_application.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.wear.compose.material.Text

@Composable
fun OrderDetailScreen(navController: NavController, orderId: String){
    Column {
        Text(text = "Order Detail Screen")
    }
}