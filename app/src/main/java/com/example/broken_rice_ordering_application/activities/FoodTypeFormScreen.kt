package com.example.broken_rice_ordering_application.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.broken_rice_ordering_application.R
import com.example.broken_rice_ordering_application.model.FoodTypeFormData
import com.example.broken_rice_ordering_application.model.toFoodTypeFormData
import com.example.broken_rice_ordering_application.model.toFoodTypeRequest
import com.example.broken_rice_ordering_application.utils.createFileFromUri
import com.example.broken_rice_ordering_application.viewModel.FoodTypeViewModel
import kotlinx.coroutines.launch

@Composable
fun FoodTypeFormScreen(
    id: String,
    navController: NavController,
    foodTypeViewModel: FoodTypeViewModel
) {
    val (foodTypeName, setFoodTypeName) = remember { mutableStateOf("") }
    //
    var foodType = if (id != "") foodTypeViewModel.getFoodTypeById(id)
        .observeAsState(initial = null).value else null

    var formData by remember(foodType) {
        mutableStateOf(foodType?.toFoodTypeFormData() ?: FoodTypeFormData())
    }
    var imageBitmap by remember {
        mutableStateOf<Bitmap?>(formData.image.let { BitmapFactory.decodeFile(it) })
    }
    //
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val fileImage = navController.context.createFileFromUri(uri,"image")
            fileImage?.let {
                val bitmap = BitmapFactory.decodeFile(fileImage.path)
                imageBitmap = bitmap
                formData = formData.copy(image = fileImage.path)
            }
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color("#252121".toColorInt()))
                    .padding(top = 15.dp, end = 15.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft, contentDescription = "back",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Cum tứm đim",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = Color.White
                )
            }
            Spacer(
                modifier = Modifier
                    .background(Color("#000000".toColorInt()))
                    .height(7.dp)
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
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center,
                ) {

                    if (imageBitmap != null) {
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

                Spacer(modifier = Modifier.height(35.dp))
                BasicTextField(
                    value = formData.name,
                    onValueChange = { name -> formData = formData.copy(name = name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (foodTypeName.isEmpty()) {
                            Text(
                                text = "Tên loại món ăn",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.height(35.dp))
                Button(
                    onClick = {
                        if (id == "") {
                            foodTypeViewModel.addFoodType(formData){ success ->
                                coroutineScope.launch {
                                    if(success){
                                        snackbarHostState.showSnackbar("Added successfully")
                                        formData = FoodTypeFormData()
                                        imageBitmap = null
                                    }else{
                                        snackbarHostState.showSnackbar("Failed to add")
                                    }
                                }
                            }
                        }else{
                            foodTypeViewModel.updateFoodType(formData.toFoodTypeRequest()){ success ->
                                coroutineScope.launch {
                                    if(success){
                                        snackbarHostState.showSnackbar("Updated successfully")
                                        navController.popBackStack()
                                    }else{
                                        snackbarHostState.showSnackbar("Failed to update")
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
                        color = Color.White
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}