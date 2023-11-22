package com.dicoding.capstonecat.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val icon: ImageVector,
    val screen: Screen,
    val contentDescription: String
)
