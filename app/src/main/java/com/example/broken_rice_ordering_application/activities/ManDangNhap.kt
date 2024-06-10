package com.example.broken_rice_ordering_application.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broken_rice_ordering_application.R

class ManDangNhap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            layOutManDangNhap()
        }
    }
}

@Composable
fun layOutManDangNhap() {
    val context = LocalContext.current

    // code giao diện bên dưới nút login
    Column(
        modifier = Modifier
            .background(Color(0xFF0282222))
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                context.startActivity(Intent(context, Login::class.java))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
            modifier = Modifier
                .width(250.dp)
                .padding(top = 600.dp, bottom = 100.dp),
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.go),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(50.dp)
                        .width(90.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.goo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                )
            }
        }
    }
    // code phần giao diện ở bên trên
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFF0252121))
            .fillMaxWidth()
            .height(500.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
    ) {
        Text(
            text = "Đăng nhập",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp, bottom = 50.dp),
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )
    }
}

@Preview
@Composable
fun PreviewManDangNhap() {
    layOutManDangNhap()
}