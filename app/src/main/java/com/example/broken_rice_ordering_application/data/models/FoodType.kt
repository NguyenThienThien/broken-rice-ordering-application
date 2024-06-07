package com.example.broken_rice_ordering_application.model

import com.google.gson.annotations.SerializedName

data class FoodType(
    val id: String,
    val name: String,
    val image: String
)

data class FoodTypeRequest(
    val id: String? = null,
    val name: String,
    val image: String
)

data class FoodTypeResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
)

fun FoodTypeResponse.toFoodType(): FoodType{
    return FoodType(
        id = this.id ?: "",
        name = this.name,
        image = this.image
    )
}
data class FoodTypeFormData(
    var id: String? = "",
    var name: String = "",
    var image: String = "",
)
fun FoodTypeFormData.toFoodTypeRequest(): FoodTypeRequest{
    val id = this.id ?: ""
    return FoodTypeRequest(
        id = id,
        name = this.name,
        image = this.image
    )
}
fun FoodType?.toFoodTypeFormData() = this?.let {
    FoodTypeFormData(
        this.id,
        this.name,
        this.image
    )
}
