package com.dicoding.capstonecat.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Camera: Screen("camera")
    object Favorite: Screen ("favorite")
    object Detail: Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
}
