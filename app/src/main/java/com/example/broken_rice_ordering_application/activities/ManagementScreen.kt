package com.example.broken_rice_ordering_application.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.broken_rice_ordering_application.R
import com.example.broken_rice_ordering_application.activities.components.RowItem
import com.example.broken_rice_ordering_application.navigation.ScreensList

@Composable
fun ManagementScreen(navController: NavController){
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color("#252121".toColorInt()))
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Cum tứm đim", fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = Color.White)
                }

                Icon(
                    Icons.Default.Notifications, contentDescription = "", modifier = Modifier.size(28.dp),
                    tint = Color("#fec340".toColorInt())
                )
            }
            Spacer(modifier = Modifier
                .background(Color("#000000".toColorInt()))
                .height(5.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color("#252121".toColorInt()))
            ) {
                RowItem(
                    text = "Quản lý loại món ăn",
                    onClick = {navController.navigate(ScreensList.FOODTYPE_MN_SCREEN.route)}
                )
//                Spacer(modifier = Modifier.height(10.dp))
                RowItem(
                    text = "Quản lý món ăn",
                    onClick = {navController.navigate(ScreensList.FOOD_MN_SCREEN.route)}
                )
            }
        }

    }
}

//@Composable
//fun RowItem( text: String, onClick: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .clickable { onClick() },
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.logo),
//            contentDescription = null,
//            modifier = Modifier
//                .size(50.dp)
//                .clip(CircleShape),
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(
//            text = text,
//            fontSize = 18.sp,
//            color = Color.White
//        )
//    }
//}