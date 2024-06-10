package com.example.broken_rice_ordering_application.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.broken_rice_ordering_application.model.Food
import com.example.broken_rice_ordering_application.model.FoodFormData
import com.example.broken_rice_ordering_application.model.toFood
import com.example.broken_rice_ordering_application.model.toFoodType
import com.example.broken_rice_ordering_application.network.RetrofitService
import com.example.broken_rice_ordering_application.utils.createPartFromDouble
import com.example.broken_rice_ordering_application.utils.createPartFromString
import com.example.broken_rice_ordering_application.utils.prepareFilePart
import kotlinx.coroutines.launch

class FoodViewModel: ViewModel() {
    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods
    init {
        getFoods()
    }
    fun getFoods(){
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.getFoods()
                if(response.isSuccessful){
                    _foods.postValue(response.body()?.map { it.toFood() })
//                    Log.d("TAG", "getFoods: "+foods)
                }else{
                    _foods.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("Tag", "getFood :" + e.message)
                _foods.postValue(emptyList())
            }
        }
    }
    fun addFood(
        formData: FoodFormData,
        onResult: (Boolean) -> Unit
    ){
        val category = createPartFromString(formData.category)
        val foodType = createPartFromString(formData.foodType)
        val name = createPartFromString(formData.name)
        val price = createPartFromDouble(formData.price)
        val imagePart = prepareFilePart("image",formData.image)
//        Log.d("TAG", "foodTypeAdd: " + formData.foodType)
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.addFood(category,name,foodType,price,imagePart)
                if(response.isSuccessful && response.body()?.status == 200){
                    getFoods()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "addFood: " + e.message)
                onResult(false)
            }
        }
    }
    fun updateFood(
        formData: FoodFormData,
        onResult: (Boolean) -> Unit
    ){
        val category = createPartFromString(formData.category)
        val foodType = createPartFromString(formData.foodType)
        val name = createPartFromString(formData.name)
        val price = createPartFromDouble(formData.price)
        val imagePart = if(formData.image.isNotEmpty()){
            prepareFilePart("image",formData.image)
        }else{
            null
        }
        viewModelScope.launch {
           try {
               val response = RetrofitService().brokenRiceApiServive.updateFood(formData.id ?: "",category,foodType,name,price,imagePart)
               if(response.isSuccessful && response.body()?.status == 200){
                   getFoods()
                   onResult(true)
               }else{
                   onResult(false)
               }
           }catch (e: Exception){
               Log.e("Tag", "updateFood: " + e.message)
               onResult(false)
           }
        }
    }
    fun deleteFood(id: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.removeFood(id)
                if (response.isSuccessful && response.body()?.status == 200) {
                    getFoods()
                    onResult(true)
                } else {
                    onResult(false)
                }
            }catch (e:Exception){
                Log.e("Tag", "deleteFood: " + e.message)
                onResult(false)
            }
        }
    }
    fun getFoodById(id: String): LiveData<Food?>{
        val livedata = MutableLiveData<Food?>()
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.getFoodDetails(id)
                if (response.isSuccessful) {
                    livedata.postValue(response.body()?.toFood())
                } else {
                    livedata.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "getFoodById: " + e.message)
                livedata.postValue(null)
            }
        }
        return livedata
    }
}