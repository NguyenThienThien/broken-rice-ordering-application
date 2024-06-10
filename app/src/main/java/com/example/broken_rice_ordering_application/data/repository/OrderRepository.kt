package com.example.broken_rice_ordering_application.data.repository

import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.OrderResponse
import com.example.broken_rice_ordering_application.data.models.orderResponseToOrder
import com.example.broken_rice_ordering_application.model.StatusResponse
import com.example.broken_rice_ordering_application.network.BrokenRiceApiServive

class OrderRepository(private val apiService: BrokenRiceApiServive) {
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

    suspend fun updateOrderStatus(orderId: String, orderResponse: Order): StatusResponse? {
        val response = apiService.updateOrder(orderId, orderResponse)
        if(response.isSuccessful){
            return response.body()
        }else{
            throw Exception("Failed to update order status")
        }
    }

}