package com.example.broken_rice_ordering_application.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.broken_rice_ordering_application.R
import com.example.broken_rice_ordering_application.activities.components.RowItem
import com.example.broken_rice_ordering_application.activities.components.ToolBar
import com.example.broken_rice_ordering_application.navigation.ScreensList

@Composable
fun FoodManageScreen(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color("#000000".toColorInt())),
    ){

        Column(
            modifier = Modifier
                .padding()
                .background(Color("#000000".toColorInt()))
        ) {
            ToolBar(navController = navController, title = "Cum tứm đim" )
            Spacer(modifier = Modifier
                .background(Color("#000000".toColorInt()))
                .height(7.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color("#252121".toColorInt()))
            ) {
                RowItem(
                    text = "Thêm món ăn",
                    onClick = {navController.navigate(ScreensList.ADD_FOOD_SCREEN.route)}
                )
                RowItem(
                    text = "Sửa món ăn",
                    onClick = {navController.navigate(ScreensList.FOOD_LIST_SCREEN_Update.route)}
                )
                RowItem(
                    text = "Xóa món ăn",
                    onClick = {navController.navigate(ScreensList.FOOD_LIST_SCREEN_Delete.route)}
                )
            }
        }

    }
}