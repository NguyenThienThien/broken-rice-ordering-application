package com.example.broken_rice_ordering_application.data.network

import com.example.broken_rice_ordering_application.data.models.Order
import com.example.broken_rice_ordering_application.data.models.OrderResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/apiT/get-listOrder")
    suspend fun getOrderList(): Response<List<OrderResponse>>

    @GET("/apiT/get-orderDetails-by-id/{id}")
    suspend fun getOrderDetails(@Path("id") id: String): Response<OrderResponse>

}