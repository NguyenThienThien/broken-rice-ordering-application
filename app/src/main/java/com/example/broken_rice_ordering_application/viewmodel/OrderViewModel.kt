package com.example.broken_rice_ordering_application.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.OrderResponse
import com.example.broken_rice_ordering_application.data.repository.OrderRepository
import com.example.broken_rice_ordering_application.network.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {

    private val _ordersLiveData = MutableLiveData<List<OrderResponse>>(emptyList())
    val ordersLiveData: LiveData<List<OrderResponse>> get() = _ordersLiveData

    private val _orderDetailLiveData = MutableLiveData<Order?>()
    val orderDetailLiveData: LiveData<Order?> get() = _orderDetailLiveData

    private val apiService = RetrofitService().brokenRiceApiServive
    private val orderRepository = OrderRepository(apiService)

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getOrderList()
                _ordersLiveData.postValue(orders)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getOrderDetail(orderId: String) {
        viewModelScope.launch {
            try {
                val orderDetail = orderRepository.getOrderDetail(orderId)
                _orderDetailLiveData.postValue(orderDetail)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateOrderStatus(orderId: String, updateOrder: Order) {
        viewModelScope.launch {
            try {
                val response = orderRepository.updateOrderStatus(orderId, updateOrder)
                fetchOrders()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadOrders() {
        fetchOrders()
    }
}
