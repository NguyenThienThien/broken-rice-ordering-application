package com.example.broken_rice_ordering_application.activities

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController

import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.OrderItem
import com.example.broken_rice_ordering_application.viewmodel.OrderViewModel
import java.text.NumberFormat
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.broken_rice_ordering_application.data.models.OrderStatus

@Composable
fun OrderDetailScreen(navController: NavController, orderId: String, orderViewModel: OrderViewModel = viewModel()) {

    LaunchedEffect(orderId) {
        orderViewModel.getOrderDetail(orderId)
    }

    var showConfirmDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    val orderDetail by orderViewModel.orderDetailLiveData.observeAsState()
    val isOrderDelivered = orderDetail?.status == OrderStatus.DELIVERED

    val confirmOrder: () -> Unit = {
        val updateOrder = orderDetail?.copy(status = OrderStatus.CONFIRMED)
        updateOrder?.let {
            orderViewModel.updateOrderStatus(orderId, it)
            navController.popBackStack()
        }
    }

    val cancelOrder: () -> Unit = {
        val updateOrder = orderDetail?.copy(status = OrderStatus.CANCELLED)
        updateOrder?.let {
            orderViewModel.updateOrderStatus(orderId, it)
            navController.popBackStack()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF252121))
                .padding(20.dp)
        ) {
            item {
                if(!isOrderDelivered){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = if (orderDetail?.status == OrderStatus.PENDING) Arrangement.SpaceBetween else Arrangement.Center
                    ) {
                        if (orderDetail?.status == OrderStatus.PENDING) {
                            ConfirmButton(
                                text = "Xác Nhận",
                                onClick = { showConfirmDialog = true },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        ConfirmButton(
                            text = "Hủy",
                            onClick = { showCancelDialog = true },
                            modifier = if (orderDetail?.status == OrderStatus.PENDING) Modifier.weight(1f) else Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            orderDetail?.let { order ->
                val sections = listOf(
                    "Món Chính" to "Món chính",
                    "Món thêm" to "Món thêm",
                    "Topping" to "Topping",
                    "Khác" to "Khác"
                )

                sections.forEach { (title, category) ->
                    item {
                        SectionTitle(title = title)
                    }
                    itemsIndexed(order.items.filter { it.category == category }) { index, dish ->
                        ListProductItem(index, dish)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Divider()
                    }
                }

                item {
                    DisplayAddress(order = order)
                    Spacer(modifier = Modifier.height(12.dp))
                    DisplayOrderDetails(
                        totalMainItems = order.items.filter { it.category == "Món chính" }.sumOf { it.quantity },
                        totalAdditionalItems = order.items.filter { it.category == "Món thêm" }.sumOf { it.quantity },
                        totalToppings = order.items.filter { it.category == "Topping" }.sumOf { it.quantity },
                        totalOthers = order.items.filter { it.category == "Khác" }.sumOf { it.quantity },
                        totalPrice = order.totalPrice,
                        customerPhone = order.customerPhone
                    )
                }
            }
        }

        if (showConfirmDialog) {
            CustomAlertDialog(
                showDialog = showConfirmDialog,
                onDismiss = { showConfirmDialog = false },
                onConfirm = {
                    showConfirmDialog = false
                    confirmOrder()
                },
                title = "Xác Nhận Đơn Hàng",
                message = "Bạn có chắc chắn muốn xác nhận đơn hàng này không?",
                confirmButtonText = "Xác Nhận",
                dismissButtonText = "Hủy",
                backgroundColor = Color(0xFF2F2D2D) // Màu nền tùy chỉnh
            )
        }

        if (showCancelDialog) {
            CustomAlertDialog(
                showDialog = showCancelDialog,
                onDismiss = { showCancelDialog = false },
                onConfirm = {
                    showCancelDialog = false
                    cancelOrder()
                },
                title = "Hủy Đơn Hàng",
                message = "Bạn có chắc chắn muốn hủy đơn hàng này không?",
                confirmButtonText = "Xác Nhận",
                dismissButtonText = "Hủy",
                backgroundColor = Color(0xFF2F2D2D) // Màu nền tùy chỉnh
            )
        }
    }
}

@Composable
fun ListProductItem(index: Int, dish: OrderItem) {
    val totalPrice = dish.priceProduct
    val formatted = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    val formattedPrice = formatted.format(totalPrice)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFF2F2D2D))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = (index + 1).toString(),
            fontSize = 20.sp,
            color = Color.White,
        )

        Spacer(modifier = Modifier.width(10.dp))

        AsyncImage(
            model = dish.imageProduct,
            contentDescription = dish.nameProduct,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = dish.nameProduct,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formattedPrice,
                fontSize = 16.sp,
                color = Color(0xFFFE724C),
                fontWeight = FontWeight(400)
            )
        }

        Text(
            text = "0${dish.quantity}",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight(400),
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DisplayAddress(order: Order) {
    Column {
        val addressParts = listOf(
            "Số nhà" to order.houseNumber,
            "Đường" to order.street,
            "Phường" to order.ward,
            "Quận" to order.district,
            "Thành phố" to order.city
        )
        addressParts.forEach { (label, value) ->
            Row {
                Text(
                    text = "$label: ",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )

                Text(
                    text = value,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }
        Divider()
    }
}

@Composable
fun DisplayOrderDetails(totalMainItems: Int, totalAdditionalItems: Int, totalToppings: Int, totalOthers: Int, totalPrice: Double, customerPhone: String) {
    val formattedPrice = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(totalPrice)
    val orderDetails = listOf(
        "Giờ" to "2h30",
        "SĐT" to customerPhone,
        "Tổng số lượng món ăn" to "$totalMainItems",
        "Tổng số lượng món ăn thêm" to "$totalAdditionalItems",
        "Tổng số lượng Topping" to "$totalToppings",
        "Tổng số lượng khác" to "$totalOthers",
        "Tổng tiền" to formattedPrice
    )

    Column {
        orderDetails.forEach { (label, value) ->
            Row {
                Text(
                    text = "$label: ",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
                Text(
                    text = value,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ConfirmButton(text: String, onClick: () -> Unit,  modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF2F2D2D),
            backgroundColor = Color(0xFF2F2D2D)
        ),
        shape = RectangleShape
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight(600)
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        color = Color.White,
        fontWeight = FontWeight(700)
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.White)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String,
    confirmButtonText: String,
    dismissButtonText: String,
    backgroundColor: Color = Color(0xFF2F2D2D) // màu nền tùy chỉnh
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = backgroundColor // Đặt màu nền tùy chỉnh
            ) {
                Column(
                    modifier = Modifier
                        .padding(29.dp)
                        .fillMaxWidth()
                        .background(backgroundColor)
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .width(100.dp)
                                .height(40.dp)
                        ) {
                            Text(dismissButtonText)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = onConfirm,
                            modifier = Modifier
                                .width(100.dp)
                                .height(40.dp)
                        ) {
                            Text(confirmButtonText)
                        }
                    }
                }
            }
        }
    }
}



