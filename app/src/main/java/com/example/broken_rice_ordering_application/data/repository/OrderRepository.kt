package com.example.broken_rice_ordering_application.data.repository

import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.OrderResponse
import com.example.broken_rice_ordering_application.data.models.orderResponseToOrder
import com.example.broken_rice_ordering_application.data.network.ApiService

class OrderRepository(private val apiService: ApiService) {
    suspend fun getOrderList(): List<OrderResponse>{
        val response = apiService.getOrderList()
        if(response.isSuccessful){
            return response.body()?: emptyList()
        }else{
            throw Exception("Failed to fetch orders")
        }
    }

    suspend fun getOrderDetail(orderId: String): Order? {
        val response = apiService.getOrderDetails(orderId)
        if(response.isSuccessful){
            return response.body()?.let { orderResponseToOrder(it) }
        }else{
            throw Exception("Failed to fetch order details")
        }
    }

}