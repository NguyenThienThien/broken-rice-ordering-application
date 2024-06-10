package com.example.broken_rice_ordering_application.model

import com.google.gson.annotations.SerializedName

data class Food(
    val id: String,
    val category: String,
    val foodType: String,
    val name: String,
    val price: Double,
    val image: String
)
data class FoodRequest(
    val id: String? = null,
    val category: String,
    val foodType: String,
    val name: String,
    val price: Double,
    val image: String
)

data class FoodResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("category") val category: String,
    @SerializedName("foodType") val foodType: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("image") val image: String
)

fun FoodResponse.toFood(): Food{
    return Food(
        id = this.id ?: "",
        category = this.category,
        foodType = this.foodType,
        name = this.name,
        price = this.price,
        image = this.image
    )
}
data class FoodFormData(
    var id: String? = "",
    var category: String ="",
    var foodType: String ="",
    var name: String ="",
    var price: Double = 0.0,
    var image: String = ""
)
fun Food?.toFoodFormData() = this?.let {
    FoodFormData(
        this.id,
        this.category,
        this.foodType,
        this.name,
        this.price,
        this.image
    )
}