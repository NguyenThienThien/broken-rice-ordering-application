package com.example.broken_rice_ordering_application.data.models

enum class OrderStatus {
    PENDING, // Đang chờ xử lý
    CONFIRMED, // Đã xác nhận
    CANCELLED, // Đã hủy
    DELIVERING, // Đang giao
    DELIVERED // Đã giao
}