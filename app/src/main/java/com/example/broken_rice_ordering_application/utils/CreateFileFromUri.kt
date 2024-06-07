package com.example.broken_rice_ordering_application.utils

import android.content.Context
import android.net.Uri
import java.io.*

fun Context.createFileFromUri(uri: Uri,name: String): File?{
    val file = File(cacheDir, "$name.png")
    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
            }
        }
        file
    }catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}