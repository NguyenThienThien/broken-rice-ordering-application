package com.example.broken_rice_ordering_application.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.broken_rice_ordering_application.R
import com.example.broken_rice_ordering_application.activities.components.CustomDropDown
import com.example.broken_rice_ordering_application.activities.components.ToolBar
import com.example.broken_rice_ordering_application.model.FoodFormData
import com.example.broken_rice_ordering_application.model.toFoodFormData
import com.example.broken_rice_ordering_application.utils.createFileFromUri
import com.example.broken_rice_ordering_application.viewModel.FoodTypeViewModel
import com.example.broken_rice_ordering_application.viewModel.FoodViewModel
import kotlinx.coroutines.launch
import java.io.FileNotFoundException

@Composable
fun FoodFormScreen(id:String,navController: NavController, foodViewModel: FoodViewModel, foodTypeViewModel: FoodTypeViewModel){
    val food = if(id != "") foodViewModel.getFoodById(id).observeAsState(initial = null).value else null
    var formData by remember(food) {
        mutableStateOf(food?.toFoodFormData()?: FoodFormData())
    }
    var imageBitmap by remember {
        mutableStateOf<Bitmap?>(formData.image.let { BitmapFactory.decodeFile(it)})
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val laucher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            Log.d("TAG", "Received URI: $uri")

            val fileImage = navController.context.createFileFromUri(uri, "image")
            Log.d("TAG", "File created: ${fileImage?.path}")

            fileImage?.let {
                try {
                    val bitmap = BitmapFactory.decodeFile(fileImage.path)
                    if (bitmap != null) {
                        imageBitmap = bitmap
                        formData = formData.copy(image = fileImage.path)
                    } else {
                        Log.e("TAG", "Failed to decode bitmap from file path: ${fileImage.path}")
                    }
                } catch (e: FileNotFoundException) {
                    Log.e("TAG", "FileNotFoundException: ${e.message}")
                }
            }
        }
    }
    // spinner
    val categories = listOf("Món chính", "Món thêm", "Topping")
    var selectedCategory = remember { categories[0]}
    //
    val foods by foodTypeViewModel.foodTypes.observeAsState(initial = emptyList())
    val foodTypes = foods.map { it.name }
    var selectedFoodType by remember { mutableStateOf(foodTypes[0])}

//    Log.d("TAG", "selectedCategory: "+selectedCategory)
//    Log.d("TAG", "selectedFoodType: "+selectedFoodType)
//    Log.d("TAG", "list types: "+foodTypes)
//    Log.d("TAG", "category choosed: "+formData.category)
//    Log.d("TAG", "type choosed: "+formData.foodType)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color("#000000".toColorInt())),
    ) {

        Column(
            modifier = Modifier
                .padding()
                .background(Color("#000000".toColorInt()))
        ) {
            ToolBar(navController = navController, title = "Cum tứm đim" )
            Spacer(
                modifier = Modifier
                    .background(Color("#000000".toColorInt()))
                    .height(5.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color("#252121".toColorInt()))
                    .padding(top = 8.dp, bottom = 70.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                        .clickable { laucher.launch("image/*") },
                    contentAlignment = Alignment.Center,
                ) {

                    if (formData.image.isNotEmpty()) {
                        Image(
                            painter = rememberImagePainter(
                                ImageRequest.Builder(context )
                                    .data(formData.image)
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(160.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else if (imageBitmap != null) {
                        Image(
                            bitmap = imageBitmap!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.add_image),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // dropdown menu chon phan loai
                    Text(
                        text = "Loại",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 7.dp)
                    )
                    CustomDropDown(
                        listItems = categories,
                        initialSelectedItem = if(id != "") formData.category else "",
                        onItemSelected = {category ->
                            selectedCategory = category
                            formData = formData.copy(category = category)
                            Log.d("TAG", "category choice: "+formData.category)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    // dropdown menu chon loai
                    Text(
                        text = "Loại",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 7.dp)
                    )
                    CustomDropDown(
                        listItems = foodTypes,
                        initialSelectedItem = if(id != "") formData.foodType else "",
                        onItemSelected = {foodType ->
                            selectedFoodType = foodType
                            formData = formData.copy(foodType = foodType)
                            Log.d("TAG", "foodType choice: "+formData.foodType)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Giá",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 7.dp)
                    )
                    BasicTextField(
                        value = if(formData.price == 0.0) "" else formData.price.toString(),
                        onValueChange = { price -> formData = formData.copy(price = price.toDoubleOrNull() ?: 0.0)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Tên món ăn",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 7.dp)
                    )
                    BasicTextField(
                        value = formData.name,
                        onValueChange = { name -> formData = formData.copy(name = name) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        singleLine = true,
                    )
                }
                Spacer(modifier = Modifier.height(35.dp))
                Button(
                    onClick = {
                        if (id == "") {
                            foodViewModel.addFood(formData){ success ->
                                coroutineScope.launch {
                                    if(success){
                                        Toast.makeText(context, "Add successfully", Toast.LENGTH_SHORT).show()
                                        formData = FoodFormData()
                                        imageBitmap = null
                                    }else{
                                        Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }else{
                            if(imageBitmap == null) formData = formData.copy(image = "")
                            foodViewModel.updateFood(formData){ success ->
                                coroutineScope.launch {
                                    if(success){
                                        Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    }else{
                                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color("#FFB703".toColorInt())),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(180.dp)
                        .clip(RoundedCornerShape(7.dp))
                ) {
                    Text(
                        text = if (id == "") "Thêm" else "Cập nhật",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}