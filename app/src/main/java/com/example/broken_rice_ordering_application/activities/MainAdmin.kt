package com.example.broken_rice_ordering_application.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.broken_rice_ordering_application.navigation.AppNavigation
import com.example.broken_rice_ordering_application.navigation.ScreenNavigation
import com.example.broken_rice_ordering_application.ui.theme.Broken_rice_ordering_applicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenNavigation()
        }
    }
}

//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = "MainHome"
//    ) {
//        composable("MainHome") { AppNavigation(navController) }
//        composable(
//            route = "OrderDetail/{orderId}",
//            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
//            OrderDetailScreen(navController = navController, orderId = orderId)
//        }
//    }
//}




