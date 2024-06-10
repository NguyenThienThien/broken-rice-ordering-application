package com.example.broken_rice_ordering_application.network

import com.example.broken_rice_ordering_application.model.FoodRequest
import com.example.broken_rice_ordering_application.model.FoodResponse
import com.example.broken_rice_ordering_application.model.FoodTypeResponse
import com.example.broken_rice_ordering_application.model.StatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface BrokenRiceApiServive {
    //===========GET===========
    @GET("/api/get-foodtypes")
    suspend fun getFoodTypes(): Response<List<FoodTypeResponse>>

    @GET("/api/get-foodtype-details/{id}")
    suspend fun getFoodTypesDetails(@Path("id") id: String): Response<FoodTypeResponse>

    @GET("/api/get-foods")
    suspend fun getFoods(): Response<List<FoodResponse>>

    @GET("/api/get-food-details/{id}")
    suspend fun getFoodDetails(@Path("id") id: String): Response<FoodResponse>

    //==========POST=============
    @Multipart
    @POST("/api/add-foodtype")
    suspend fun addFoodType(
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<StatusResponse>

    @Multipart
    @POST("/api/add-food")
    suspend fun addFood(
        @Part("category") category: RequestBody,
        @Part("name") name: RequestBody,
        @Part("foodType") foodType: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<StatusResponse>

    //==========PUT=============
    @Multipart
    @PUT("/api/update-foodtype/{id}")
    suspend fun updateFoodType(
        @Path("id") id: String,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<StatusResponse>

    @Multipart
    @PUT("/api/update-food/{id}")
    suspend fun updateFood(
        @Path("id") id: String,
        @Part("category") category: RequestBody,
        @Part("foodType") foodType: RequestBody,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<StatusResponse>

    //==========DELETE=============
    @DELETE("/api/delete-foodtype/{id}")
    suspend fun removeFoodType(@Path("id") id: String): Response<StatusResponse>

    @DELETE("/api/delete-food/{id}")
    suspend fun removeFood(@Path("id") id: String): Response<StatusResponse>

}