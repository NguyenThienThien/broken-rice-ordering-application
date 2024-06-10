package com.example.broken_rice_ordering_application.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.broken_rice_ordering_application.activities.FoodFormScreen
import com.example.broken_rice_ordering_application.activities.FoodListScreen
import com.example.broken_rice_ordering_application.activities.FoodManageScreen
import com.example.broken_rice_ordering_application.activities.FoodTypeFormScreen
import com.example.broken_rice_ordering_application.activities.FoodTypeListScreen
import com.example.broken_rice_ordering_application.activities.FoodTypeManageScreen
import com.example.broken_rice_ordering_application.viewModel.FoodTypeViewModel
import com.example.broken_rice_ordering_application.viewModel.FoodViewModel

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()
    val foodTypeViewModel: FoodTypeViewModel = viewModel()
    val foodViewModel: FoodViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "tabNav"
    ) {
        composable(ScreensList.TABNAV.route) { AppNavigation( navController) }
        //
        composable(ScreensList.FOODTYPE_MN_SCREEN.route){ FoodTypeManageScreen(navController = navController)}
        composable(ScreensList.ADD_FOODTYPE_SCREEN.route){
            FoodTypeFormScreen(id = "", navController,foodTypeViewModel)
        }
        composable("${ScreensList.UPDATE_FOODTYPE_SCREEN.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){ backStackEntry ->
            val foodTypeId = backStackEntry.arguments?.getString("id")
            foodTypeId.let { FoodTypeFormScreen(id = foodTypeId ?: "", navController,foodTypeViewModel ) }
        }
        composable(ScreensList.FOODTYPE_LIST_SCREEN_Update.route){
            FoodTypeListScreen(status = "edit", navController = navController,foodTypeViewModel)
        }
        composable(ScreensList.FOODTYPE_LIST_SCREEN_Delete.route){
            FoodTypeListScreen(status = "remove", navController = navController,foodTypeViewModel)
        }
        //
        composable(ScreensList.FOOD_MN_SCREEN.route){ FoodManageScreen(navController = navController)}
        composable(ScreensList.ADD_FOOD_SCREEN.route){
            FoodFormScreen(id = "", navController,foodViewModel,foodTypeViewModel)
        }
        composable("${ScreensList.UPDATE_FOOD_SCREEN.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("id")
            foodId.let { FoodFormScreen(id = foodId ?: "", navController,foodViewModel,foodTypeViewModel) }
        }
        composable(ScreensList.FOOD_LIST_SCREEN_Update.route){
            FoodListScreen(status = "edit", navController = navController,foodViewModel)
        }
        composable(ScreensList.FOOD_LIST_SCREEN_Delete.route){
            FoodListScreen(status = "remove", navController = navController,foodViewModel)
        }
    }
}