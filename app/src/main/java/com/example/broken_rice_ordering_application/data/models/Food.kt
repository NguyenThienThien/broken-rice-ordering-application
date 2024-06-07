package com.example.broken_rice_ordering_application.model

import com.google.gson.annotations.SerializedName

data class Food(
    val id: String,
    val name: String,
    val price: Double,
    val image: String
)
data class FoodRequest(
    val id: String? = null,
    val name: String,
    val price: Double,
    val image: String
)

data class FoodResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("image") val image: String
)

fun FoodResponse.toFood(): Food{
    return Food(
        id = this.id ?: "",
        name = this.name,
        price = this.price,
        image = this.image
    )
}