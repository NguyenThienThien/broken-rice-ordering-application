package com.example.broken_rice_ordering_application.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.broken_rice_ordering_application.activities.HomeScreen
import com.example.broken_rice_ordering_application.activities.ManagementScreen
import com.example.broken_rice_ordering_application.activities.StatisticsScreen
import com.example.broken_rice_ordering_application.activities.SupportScreen

@Composable
fun AppNavigation(navigationController: NavController){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF312C2C)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavitems.forEach { navItem ->
                    val selected = currentDestination?.hierarchy?.any{ it.route == navItem.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                                  navController.navigate(navItem.route){
                                      popUpTo(navController.graph.findStartDestination().id){
                                          saveState = true
                                      }
                                      launchSingleTop = true
                                      restoreState = true
                                  }
                        },
                        icon = {
                            val iconColor = if (selected) Color(0xFFFFA000) else Color.White // Màu icon khi được chọn và không chọn
                            Image(
                                painter = painterResource(id = navItem.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp),
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconColor)
                            )
                        },
                        label = {
                            val labelColor = if (selected) Color(0xFFFFA000) else Color.White
                            Text(
                                text = navItem.label,
                                color = labelColor,
                                modifier = Modifier
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFFFA000),
                            unselectedIconColor = Color.White,
                            selectedTextColor = Color(0xFFFFA000),
                            unselectedTextColor = Color.White,
                            indicatorColor = Color.Transparent // Loại bỏ màu nền khi được chọn
                        )
                    )
                }
            }
        }
    ) {
        paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name){
                HomeScreen(navController = navigationController)
            }

            composable(route = Screens.StatisticsScreen.name){
                StatisticsScreen()
            }

            composable(route = Screens.ManagementScreen.name){
                ManagementScreen()
            }

            composable(route = Screens.SupportScreen.name){
                SupportScreen()
            }
        }
    }
}