package com.example.broken_rice_ordering_application.data.models

import com.google.gson.annotations.SerializedName

data class OrderItem(
    val category: String,
    val nameProduct: String,
    val imageProduct: String,
    val priceProduct: Double,
    val quantity: Int
)

data class Order(
    val id: String,
    val customerName: String,
    val customerPhone: String,
    val items: List<OrderItem>,
    val status: OrderStatus,
    val totalMainItems: Int,
    val totalAdditionalItems: Int,
    val totalToppings: Int,
    val totalOthers: Int,
    val totalPrice: Double,
    val houseNumber: String,
    val street: String,
    val ward: String,
    val district: String,
    val city: String,
    val date: String
)

data class OrderResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("customerName") val customerName: String,
    @SerializedName("customerPhone") val customerPhone: String,
    @SerializedName("items") val items: List<OrderItem>,
    @SerializedName("status") val status: OrderStatus,
    @SerializedName("totalMainItems") val totalMainItems: Int,
    @SerializedName("totalAdditionalItems") val totalAdditionalItems: Int,
    @SerializedName("totalToppings") val totalToppings: Int,
    @SerializedName("totalOthers") val totalOthers: Int,
    @SerializedName("totalPrice") val totalPrice: Double,
    @SerializedName("houseNumber") val houseNumber: String,
    @SerializedName("street") val street: String,
    @SerializedName("ward") val ward: String,
    @SerializedName("district") val district: String,
    @SerializedName("city") val city: String,
    @SerializedName("date") val date: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
)

fun orderResponseToOrder(response: OrderResponse) : Order {
    return Order(
        id = response.id,
        customerName = response.customerName,
        customerPhone = response.customerPhone,
        items = response.items,
        status = response.status,
        totalMainItems = response.totalMainItems,
        totalAdditionalItems = response.totalAdditionalItems,
        totalToppings = response.totalToppings,
        totalOthers = response.totalOthers,
        totalPrice = response.totalPrice,
        houseNumber = response.houseNumber,
        street = response.street,
        ward = response.ward,
        district = response.district,
        city = response.city,
        date = response.date,
    )
}


