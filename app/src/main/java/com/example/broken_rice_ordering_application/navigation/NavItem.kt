package com.example.broken_rice_ordering_application.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.broken_rice_ordering_application.R

data class NavItem(
    val label: String,
    val icon: Int,
    val route: String
)

val listOfNavitems = listOf(
    NavItem(
        label = "Trang chủ",
        icon = R.drawable.icon_home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Thống kê",
        icon = R.drawable.icon_statistics,
        route = Screens.StatisticsScreen.name
    ),
    NavItem(
        label = "Quản lý",
        icon = R.drawable.icon_manage,
        route = Screens.ManagementScreen.name
    ),
    NavItem(
        label = "Hỗ trợ",
        icon = R.drawable.icon_person,
        route = Screens.SupportScreen.name
    )
)