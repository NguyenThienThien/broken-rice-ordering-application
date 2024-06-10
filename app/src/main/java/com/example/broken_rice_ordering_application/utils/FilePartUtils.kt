package com.example.broken_rice_ordering_application.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun createPartFromString(value: String): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
}
fun createPartFromDouble(value: Double): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), value.toString())
}
fun prepareFilePart(partName: String, fileUri: String): MultipartBody.Part {
    val file = File(fileUri)
    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}