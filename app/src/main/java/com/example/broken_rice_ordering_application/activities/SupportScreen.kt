package com.example.broken_rice_ordering_application.activities

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.broken_rice_ordering_application.R
import kotlinx.coroutines.delay

@Composable
fun SupportScreen(){
    LayoutHelpScreen()
}

@Preview
@Composable
fun LayoutHelpScreen() {
    val imageIds = listOf(
        R.drawable.zalo,
        R.drawable.gmail,
        R.drawable.phone
    )
    val texts = listOf(
        "0981139895",
        "email@gmail.com",
        "0981139895"
    )

    var delayOffset = 0L // Biến để định trễ cho mỗi hàng

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFF252121)),
        verticalArrangement = Arrangement.Center
    ) {
        imageIds.forEachIndexed { index, imageResId ->
            val delayMillis = delayOffset + index * 1000L // Mỗi hàng bắt đầu lắc sau 1 giây so với hàng trước đó
            ShakingImageRow(
                imageResId = imageResId,
                text = texts[index],
                delayMillis = delayMillis
            )
            delayOffset += 100L // Tăng định trễ cho hàng tiếp theo
        }
    }
}

@Composable
fun ShakingImageRow(imageResId: Int, text: String, delayMillis: Long) {
    var startShaking by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayMillis) // Đợi thời gian tương ứng trước khi bắt đầu lắc
        startShaking = true
    }

    val translationX by animateFloatAsState(
        if (startShaking) 20f else 0f, // Tăng giá trị của translationX lên
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(300.dp)
            .padding(top = 50.dp)
            .graphicsLayer(
                translationX = translationX
            )
            .background(Color.White)
            .border(1.dp, Color(0xFF252121))
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )
        Text(
            text = text,
            color = Color.Red,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}