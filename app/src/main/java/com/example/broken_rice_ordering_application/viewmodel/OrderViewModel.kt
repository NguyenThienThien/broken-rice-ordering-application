package com.example.broken_rice_ordering_application.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.OrderResponse
import com.example.broken_rice_ordering_application.data.network.RetrofitService
import com.example.broken_rice_ordering_application.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {
    private val _orders = MutableStateFlow<List<OrderResponse>>(emptyList())
    val orders: StateFlow<List<OrderResponse>> get() = _orders

    private val _orderDetail = MutableStateFlow<Order?>(null)
    val orderDetail: StateFlow<Order?> get() = _orderDetail

    private val apiService = RetrofitService().ApiServive
    private val orderRepository = OrderRepository(apiService)

    init{
        fetchOrders()
    }

    private fun fetchOrders(){
        viewModelScope.launch {
            try {
                val orders = orderRepository.getOrderList()
                _orders.value = orders
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun getOrderDetail(orderId: String){
        viewModelScope.launch {
            try {
                val orderDetail = orderRepository.getOrderDetail(orderId)
                _orderDetail.value = orderDetail
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}