package com.example.broken_rice_ordering_application.activities

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Text
import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.getSampleOrders
import java.text.NumberFormat
import java.util.Locale


@Composable
fun HomeScreen(navController: NavController){
    
    val orders = getSampleOrders()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF252121))
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(10.dp))

            Row {
                Text(
                    text = "Today: ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 20.sp,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "19-05-2023",
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 20.sp,
                    letterSpacing = 3.sp
                )
            }

            Spacer(modifier = Modifier.padding(2.dp))

            Row {
                Text(
                    text = "Số lượng đơn: ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 20.sp,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "2",
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 20.sp,
                    letterSpacing = 3.sp
                )
            }

            Spacer(modifier = Modifier.padding(2.dp))

            Row {
                Text(
                    text = "Doanh thu : ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 20.sp,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "320.000 đ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFE724C),
                    lineHeight = 20.sp,
                    letterSpacing = 3.sp
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            OrderList(orders = orders)

        }
    }
}

@Composable
fun OrderList(orders: List<Order>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        items(orders){ order ->
            OrderItemColumn(order = order)
        }
    }
}

@Composable
fun OrderItemColumn(order: Order){

    val totalPrice = order.totalPrice
    val formatted = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    val formattedPrice = formatted.format(totalPrice)

    Spacer(modifier = Modifier.padding(5.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF2F2D2D))
            .height(110.dp)
            .padding(10.dp)
            .clickable {},
        verticalArrangement = Arrangement.Center
    ) {

        Row {
            Text(
                text = "Đơn hàng ",
                fontWeight = FontWeight(700),
                fontSize = 17.sp,
                lineHeight = 25.sp,
            )

            Text(
                text = order.id,
                fontWeight = FontWeight(700),
                fontSize = 17.sp,
                lineHeight = 25.sp,
                modifier = Modifier
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(18.dp)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.padding(1.dp))

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(18.dp)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = formattedPrice,
                fontWeight = FontWeight(700),
                fontSize = 17.sp,
                lineHeight = 25.sp,
                textAlign = TextAlign.End,
                modifier = Modifier

            )
        }

        Spacer(modifier = Modifier.padding(2.dp))

        Row {
            Text(
                text = "Trạng thái ",
                fontWeight = FontWeight(700),
                fontSize = 17.sp,
                lineHeight = 25.sp,
            )

            Text(
                text = "Từ chối",
                fontWeight = FontWeight(700),
                fontSize = 17.sp,
                lineHeight = 25.sp,
                color = Color.Red,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
@Preview
fun HomeScreenPreview(){
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}