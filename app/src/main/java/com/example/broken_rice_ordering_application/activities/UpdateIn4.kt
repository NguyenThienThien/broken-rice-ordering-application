package com.example.broken_rice_ordering_application.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broken_rice_ordering_application.R

class UpdateIn4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayOutUpdateIn4()
        }
    }
}


@Preview
@Composable
fun LayOutUpdateIn4() {
    Column(
        modifier = Modifier
            .background(Color(0xFF0282222))
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )
        Text(
            text = "Hello Phúc",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Vui lòng nhập thông tin chính xác để thuận tiện cho việc giao hàng ",
            color = Color.White,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 70.dp, end = 70.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 20.dp),
        ) {
            Text(
                text = "Số điện thoại ",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 3.dp)
            )
            var phoneNumber by remember { mutableStateOf("") }

            // TextField for input
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Nhập số điện thoại") },
                modifier = Modifier
                    .padding(start = 3.dp, top = 8.dp)
                    .width(340.dp)
            )
            Text(
                text = "Phường ",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 3.dp)
            )
            var tenPhuong by remember { mutableStateOf("") }

            // TextField for input
            OutlinedTextField(
                value = tenPhuong,
                onValueChange = { tenPhuong = it },
                label = { Text("Nhập tên phường ") },
                modifier = Modifier
                    .padding(start = 3.dp, top = 8.dp)
                    .width(340.dp)
            )
            Text(
                text = "Đường",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 3.dp)
            )
            var tenDuong by remember { mutableStateOf("") }

            // TextField for input
            OutlinedTextField(
                value = tenDuong,
                onValueChange = { tenDuong = it },
                label = { Text("Nhập số đường") },
                modifier = Modifier
                    .padding(start = 3.dp, top = 8.dp)
                    .width(340.dp)
            )
            Text(
                text = "Số nhà ",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 3.dp)
            )
            var soNha by remember { mutableStateOf("") }

            // TextField for input
            OutlinedTextField(
                value = soNha,
                onValueChange = { soNha = it },
                label = { Text("Nhập số Nhà") },
                modifier = Modifier
                    .padding(start = 3.dp, top = 8.dp)
                    .width(340.dp)
            )
        }
    }

}