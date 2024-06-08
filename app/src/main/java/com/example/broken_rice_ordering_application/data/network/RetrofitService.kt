package com.example.broken_rice_ordering_application.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val ApiServive: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}