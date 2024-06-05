package com.example.broken_rice_ordering_application.data.models

data class OrderItem(
    val category: String,
    val name: String,
    val price: Int,
    val quantity: Int
)

data class Address(
    val houseNumber: String,
    val street: String,
    val ward: String,
    val district: String,
    val city: String
)

data class Order(
    val id: String,
    val customerName: String,
    val customerPhone: String,
    val address: Address,
    val items: List<OrderItem>,
    val status: OrderStatus,
    val totalMainItems: Int,
    val totalAdditionalItems: Int,
    val totalToppings: Int,
    val totalOthers: Int,
    val totalPrice: Int
)

fun getSampleOrders(): List<Order> {
    val items1 = listOf(
        OrderItem("Món chính", "Sườn bì", 60000, 2),
        OrderItem("Món chính", "Bì chả", 25000, 1),
        OrderItem("Món chính", "Bì trứng", 25000, 1),
        OrderItem("Món thêm", "Sườn", 10000, 1),
        OrderItem("Món thêm", "Sườn mỡ", 10000, 1),
        OrderItem("Món thêm", "Trứng", 5000, 1),
        OrderItem("Topping", "Hành phi", 0, 1),
        OrderItem("Topping", "Tóp mỡ", 0, 1),
        OrderItem("Khác", "Khăn lạnh", 2000, 1),
        OrderItem("Khác", "Kèm súp", 0, 1)
    )

    val address1 = Address(
        houseNumber = "54",
        street = "Đường 14",
        ward = "Đông Hưng Thuận",
        district = "12",
        city = "Hồ Chí Minh"
    )

    val order1 = Order(
        id = "#1",
        customerName = "Cơm tấm 8m",
        customerPhone = "0906070364",
        address = address1,
        items = items1,
        status = OrderStatus.CONFIRMED,
        totalMainItems = items1.filter { it.category == "Món chính" }.sumOf { it.quantity },
        totalAdditionalItems = items1.filter { it.category == "Món thêm" }.sumOf { it.quantity },
        totalToppings = items1.filter { it.category == "Topping" }.sumOf { it.quantity },
        totalOthers = items1.filter { it.category == "Khác" }.sumOf { it.quantity },
        totalPrice = items1.sumOf { it.price * it.quantity }
    )

    val order2 = Order(
        id = "#2",
        customerName = "Cơm tấm 8m",
        customerPhone = "0906070364",
        address = address1,
        items = items1,
        status = OrderStatus.CONFIRMED,
        totalMainItems = items1.filter { it.category == "Món chính" }.sumOf { it.quantity },
        totalAdditionalItems = items1.filter { it.category == "Món thêm" }.sumOf { it.quantity },
        totalToppings = items1.filter { it.category == "Topping" }.sumOf { it.quantity },
        totalOthers = items1.filter { it.category == "Khác" }.sumOf { it.quantity },
        totalPrice = items1.sumOf { it.price * it.quantity }
    )

    return listOf(order1, order2)
}
