package com.example.broken_rice_ordering_application.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.broken_rice_ordering_application.model.FoodType
import com.example.broken_rice_ordering_application.model.FoodTypeFormData
import com.example.broken_rice_ordering_application.model.FoodTypeRequest
import com.example.broken_rice_ordering_application.model.toFoodType
import com.example.broken_rice_ordering_application.network.RetrofitService
import com.example.broken_rice_ordering_application.utils.createPartFromString
import com.example.broken_rice_ordering_application.utils.prepareFilePart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FoodTypeViewModel : ViewModel() {
    private val _foodTypes = MutableLiveData<List<FoodType>>()
    val foodTypes: LiveData<List<FoodType>> = _foodTypes

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    init {
        getFoodTypes()
    }

    fun getFoodTypes() {
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.getFoodTypes()
                if (response.isSuccessful) {
                    _foodTypes.postValue(response.body()?.map { it.toFoodType() })
                } else {
                    _foodTypes.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("Tag", "getFoodType :" + e.message)
                _foodTypes.postValue(emptyList())
            }
        }
    }

//    fun createPartFromString(value: String): RequestBody {
//        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
//    }
//
//    fun prepareFilePart(partName: String, fileUri: String): MultipartBody.Part {
//        val file = File(fileUri)
//        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
//    }

    fun addFoodType(
        formData: FoodTypeFormData,
        onResult: (Boolean) -> Unit
    ) {
        val name = createPartFromString(formData.name)
        val imagePart = prepareFilePart("image", formData.image)

        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.addFoodType(name, imagePart)
                if (response.isSuccessful && response.body()?.status == 200) {
                    getFoodTypes()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "addFoodType: " + e.message)
                onResult(false)
            }
        }
    }

    fun updateFoodType(
        formData: FoodTypeFormData,
        onResult: (Boolean) -> Unit
    ) {
        val name = createPartFromString(formData.name)
        val imagePart = if (formData.image.isNotEmpty()) {
            prepareFilePart("image", formData.image)
        } else {
            null
        }
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.updateFoodType(
                    formData.id ?: "",
                    name,
                    imagePart
                )

                if (response.isSuccessful && response.body()?.status == 200) {
                    getFoodTypes()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "updateFoodType: " + e.message)
                onResult(false)
            }
        }
    }

    fun deleteFoodType(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.removeFoodType(id)
                if (response.isSuccessful && response.body()?.status == 200) {
                    getFoodTypes()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "deleteTodo: " + e.message)
                onResult(false)
            }
        }
    }

    fun getFoodTypeById(id: String): LiveData<FoodType?> {
        val livedata = MutableLiveData<FoodType?>()
        viewModelScope.launch {
            try {
                val response = RetrofitService().brokenRiceApiServive.getFoodTypesDetails(id)
                if (response.isSuccessful) {
                    livedata.postValue(response.body()?.toFoodType())
                } else {
                    livedata.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("Tag", "getFoodTypeById: " + e.message)
                livedata.postValue(null)
            }
        }
        return livedata
    }
}