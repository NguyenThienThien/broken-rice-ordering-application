package com.example.broken_rice_ordering_application.activities

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.broken_rice_ordering_application.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.broken_rice_ordering_application.activities.components.ItemFoodType
import com.example.broken_rice_ordering_application.navigation.ScreensList
import com.example.broken_rice_ordering_application.viewModel.FoodTypeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun FoodTypeListScreen(status: String,navController: NavController, foodTypeViewModel: FoodTypeViewModel){
    Log.d("TAG", "status: "+status)
    val foodTypeState = foodTypeViewModel.foodTypes.observeAsState(initial = emptyList())
    var listTypes = foodTypeState.value
    val context = LocalContext.current
    val isSuccess by foodTypeViewModel.isSuccess.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color("#000000".toColorInt())),
    ){

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
                Text(text = "Cum tứm đim", fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = Color.White)
            }
            Spacer(modifier = Modifier
                .background(Color("#000000".toColorInt()))
                .height(7.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color("#252121".toColorInt()))
                    .padding(top = 8.dp)
            ) {
                items(listTypes.size){ index ->
                    //
                    ItemFoodType(
                        index = index +1,
                        foodType = listTypes[index],
                        status = status,
                        onClickHandle = {id ->
                            if(status == "remove"){
                                foodTypeViewModel.deleteFoodType(id){success ->
                                    coroutineScope.launch {
                                        if(success){
                                            snackbarHostState.showSnackbar("Deleted successfully")
                                        }else{
                                            snackbarHostState.showSnackbar("Failed to delete")
                                        }
                                    }
                                }

                            }else{
                                navController.navigate("${ScreensList.UPDATE_FOODTYPE_SCREEN.route}/${id}")
                            }
                        }
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
//    isSuccess?.let { success ->
//        LaunchedEffect(success) {
//            if (success) {
//                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}
