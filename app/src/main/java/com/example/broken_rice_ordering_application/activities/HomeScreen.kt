package com.example.broken_rice_ordering_application.activities

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Text
import com.example.broken_rice_ordering_application.activities.components.ToolBar
import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.OrderStatus
import com.example.broken_rice_ordering_application.data.models.orderResponseToOrder
import com.example.broken_rice_ordering_application.utils.getStatusText
import com.example.broken_rice_ordering_application.viewmodel.OrderViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun HomeScreen(navController: NavController, orderViewModel: OrderViewModel = viewModel()){

    LaunchedEffect(Unit) {
        orderViewModel.loadOrders()
    }
    val orders by orderViewModel.ordersLiveData.observeAsState(emptyList())
    val currentDate = getCurrentDateFormatted()

    // lọc các đơn hàng có trạng thái DELIVERED và ngày hiện tại
    val deliveredOrdersToday = orders.filter {
        it.status == OrderStatus.DELIVERED && isSameDay(it.date, currentDate)
    }
    val totalRevenue = deliveredOrdersToday.sumOf { it.totalPrice }
    val formattedRevenue = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(totalRevenue)

    // lọc số lương đơn hàng theo ngày
    val ListOrdersToday = orders.filter { isSameDay(it.date, currentDate) }

    // sắp xếp các đơn hàng theo trạng thái
    val sortedOrders = ListOrdersToday.sortedWith(compareBy{ getOrderStatusPriority(it.status)})

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
            ToolBar(navController = navController, title = "Cum túm đim")
            DividerHome()
            Spacer(modifier = Modifier.padding(2.dp))
            TextRow("Today: ", currentDate)
            TextRow("Số lượng đơn: ", ListOrdersToday.size.toString())
            TextRow("Doanh thu : ", formattedRevenue, Color(0xFFFE724C))
            Spacer(modifier = Modifier.padding(5.dp))
            OrderList(navController = navController, orders = sortedOrders.map { orderResponseToOrder(it) })
        }
    }
}

@Composable
fun OrderList(navController: NavController, orders: List<Order>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        itemsIndexed(orders) { index, order ->
            OrderItemColumn(navController = navController, order = order, index = index + 1)
        }
    }
}

@Composable
fun OrderItemColumn(navController: NavController, order: Order, index: Int){

    val totalPrice = order.totalPrice
    val formatted = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    val formattedPrice = formatted.format(totalPrice)

    Spacer(modifier = Modifier.padding(5.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2F2D2D))
            .height(110.dp)
            .padding(20.dp)
            .clickable {
                navController.navigate("OrderDetail/${order.id}")
            },
        verticalArrangement = Arrangement.Center
    ) {

        Row {
            Text(
                text = "Đơn hàng ${order.id}",
                fontWeight = FontWeight(700),
                fontSize = 14.sp,
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
                fontSize = 14.sp,
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
                fontSize = 14.sp,
                lineHeight = 25.sp,
            )

            Text(
                text = getStatusText(order.status),
                fontWeight = FontWeight(700),
                fontSize = 15.sp,
                lineHeight = 25.sp,
                color = getColorForStatus(order.status),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
fun TextRow(label: String, value: String, color: Color? = null) {
    Row {
        Text(
            text = label,
            fontSize = 17.sp,
            fontWeight = FontWeight(700),
            lineHeight = 20.sp,
            letterSpacing = 3.sp
        )
        Text(
            text = value,
            fontSize = 17.sp,
            fontWeight = FontWeight(700),
            lineHeight = 20.sp,
            letterSpacing = 3.sp,
            color = color ?: Color.Unspecified
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDateFormatted() : String {
    val currentDate = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    return formatter.format(currentDate)
}

fun isSameDay(dateString: String, currentDate: String): Boolean {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(formatter.parse(dateString)!!) == currentDate
}

fun getColorForStatus(status: OrderStatus): Color {
    return when (status) {
        OrderStatus.PENDING -> Color.Red
        OrderStatus.CONFIRMED -> Color.Green
        OrderStatus.DELIVERING -> Color.Green
        OrderStatus.DELIVERED -> Color.Cyan
        OrderStatus.CANCELLED -> Color.Red
    }
}

fun getOrderStatusPriority(status: OrderStatus): Int {
    return when (status) {
        OrderStatus.PENDING -> 1
        OrderStatus.CONFIRMED -> 2
        OrderStatus.DELIVERING -> 3
        OrderStatus.DELIVERED -> 4
        OrderStatus.CANCELLED -> 5
    }
}

@Composable
fun DividerHome() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .background(Color.Black)
    )
    Spacer(modifier = Modifier.height(20.dp))
}