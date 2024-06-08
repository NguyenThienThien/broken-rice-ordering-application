package com.example.broken_rice_ordering_application.utils

import com.example.broken_rice_ordering_application.data.models.OrderStatus

fun getStatusText(status: OrderStatus): String {
    return when (status) {
        OrderStatus.PENDING -> "Đang chờ xử lý"
        OrderStatus.CONFIRMED -> "Đã xác nhận"
        OrderStatus.DELIVERING -> "Đang giao"
        OrderStatus.DELIVERED -> "Đã giao"
        OrderStatus.CANCELLED -> "Đã hủy"
    }
}