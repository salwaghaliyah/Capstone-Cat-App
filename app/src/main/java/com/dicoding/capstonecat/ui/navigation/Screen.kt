package com.dicoding.capstonecat.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Camera: Screen("camera")
    object Favorite: Screen ("favorite")
    object Detail: Screen("home/{breed}") {
        fun createRoute(breed: String) = "home/$breed"
    }
}
