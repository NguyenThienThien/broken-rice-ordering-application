package com.example.broken_rice_ordering_application.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.broken_rice_ordering_application.model.FoodType
import com.example.broken_rice_ordering_application.model.FoodTypeRequest
import com.example.broken_rice_ordering_application.model.toFood
import com.example.broken_rice_ordering_application.model.toFoodType
import com.example.broken_rice_ordering_application.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodTypeRepository(private val retrofitService: RetrofitService) {
    private val _foodTypes = MutableLiveData<List<FoodType>>()
    var foodTypes: LiveData<List<FoodType>> = _foodTypes

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    init {
        getFoodTypes()
    }
    fun getFoodTypes(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitService.brokenRiceApiServive.getFoodTypes()
                if(response.isSuccessful){
                    _foodTypes.postValue(response.body()?.map { it.toFoodType()})
                }else{
                    _foodTypes.postValue(emptyList())
                }
            }catch (e:Exception){
                Log.e("Tag","getFoodTypes : "+e.message)
                _foodTypes.postValue(emptyList())
            }
        }
    }

//    fun addFoodType(foodTypeRequest: FoodTypeRequest, onComplete: (Boolean) -> Unit) {
//        CoroutineScope(Dispatchers.IO).launch {
//            _isSuccess.postValue(
//                try {
//                    val response = retrofitService.brokenRiceApiServive.addFoodType(foodTypeRequest)
//                    if(response.isSuccessful){
//                        response.body()?.let {
//                            if(it.status == 1){
//                                getFoodTypes()
//                                true
//                            }else{
//                                false
//                            }
//                        }?: false
//                    }else{
//                        false
//                    }
//                }catch (e:Exception){
//                    Log.e("Tag", "addFoodType: " + e.message)
//                    false
//                }
//            )
//        }
//    }
    fun getFoodTypeById(id: String): LiveData<FoodType?>{
        val livedata = MutableLiveData<FoodType?>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitService.brokenRiceApiServive.getFoodTypesDetails(id)
                if(response.isSuccessful){
                    livedata.postValue(response.body()?.toFoodType())
                }else{
                    livedata.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "getFoodTypeById: " + e.message)
                livedata.postValue(null)
            }
        }
        return livedata
    }
//    fun updateFoodType(
//        foodTypeRequest: FoodTypeRequest,
//        onComplete: (Boolean) -> Unit
//    ){
//        CoroutineScope(Dispatchers.IO).launch {
//            _isSuccess.postValue(
//                try {
//                    val response = retrofitService.brokenRiceApiServive.updateFoodType(foodTypeRequest.id ?:"", foodTypeRequest)
//                    if(response.isSuccessful){
//                        response.body()?.let {
//                            if (it.status == 1) {
//                                getFoodTypes()
//                                withContext(Dispatchers.Main) {
//                                    onComplete
//                                }
//                                true
//                            } else {
//                                false
//                            }
//                        }?: false
//                    }else{
//                        false
//                    }
//                }catch (e: Exception) {
//                    Log.e("Tag", "updateFoodType: " + e.message)
//                    false
//                }
//            )
//        }
//    }
    fun deleteFoodType(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            _isSuccess.postValue(
                try {
                    val response = retrofitService.brokenRiceApiServive.removeFoodType(id)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.status == 1) {
                                getFoodTypes()
                                true
                            } else {
                                false
                            }
                        } ?: false
                    } else {
                        false
                    }
                } catch (e: Exception) {
                    Log.e("Tag", "deleteTodo: " + e.message)
                    false
                }
            )
        }
    }
}