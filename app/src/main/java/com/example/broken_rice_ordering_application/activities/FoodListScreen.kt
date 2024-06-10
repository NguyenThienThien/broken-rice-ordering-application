package com.example.broken_rice_ordering_application.activities

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.broken_rice_ordering_application.activities.components.DialogConfirmDelete
import com.example.broken_rice_ordering_application.activities.components.ItemFood
import com.example.broken_rice_ordering_application.activities.components.ItemFoodType
import com.example.broken_rice_ordering_application.activities.components.ToolBar
import com.example.broken_rice_ordering_application.navigation.ScreensList
import com.example.broken_rice_ordering_application.viewModel.FoodViewModel
import kotlinx.coroutines.launch

@Composable
fun FoodListScreen(status:String,navController: NavController,foodViewModel: FoodViewModel){
    Log.d("TAG", "status: "+status)
    val foodState = foodViewModel.foods.observeAsState(initial = emptyList())
    val listFoods = foodState.value
    Log.d("TAG", "listFoods: "+listFoods)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showDialogDelete by remember { mutableStateOf(false) }
    var foodIdToDelete by remember { mutableStateOf("") }

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
            ToolBar(navController = navController, title = "Cum tứm đim" )
            Spacer(modifier = Modifier
                .background(Color("#000000".toColorInt()))
                .height(5.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color("#252121".toColorInt()))
                    .padding(top = 8.dp)
            ) {
                items(listFoods.size){ index ->
                    //
                    ItemFood(
                        index = index +1,
                        food = listFoods[index],
                        status = status,
                        onClickHandle = {id ->
                            if(status == "remove"){
                                foodIdToDelete = id
                                showDialogDelete = true
                            }else{
                                navController.navigate("${ScreensList.UPDATE_FOOD_SCREEN.route}/${id}")
                            }
                        }
                    )
                }
            }
        }
    }
    foodIdToDelete.let { id ->
        if(showDialogDelete){
            DialogConfirmDelete(
                title = "Bạn có chắc chắn muốn xóa loại món ăn này không ?",
                onConfirm = {
                    foodViewModel.deleteFood(id){ success ->
                        coroutineScope.launch {
                            if(success){
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    showDialogDelete = false
                    foodIdToDelete = ""
                },
                onDismiss = {
                    showDialogDelete = false
                    foodIdToDelete = ""
                }
            )
        }
    }
}