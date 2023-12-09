package com.dicoding.capstonecat

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.capstonecat.ui.navigation.NavigationItem
import com.dicoding.capstonecat.ui.navigation.Screen
import com.dicoding.capstonecat.ui.screen.camera.CameraScreen
import com.dicoding.capstonecat.ui.screen.detail.DetailScreen
import com.dicoding.capstonecat.ui.screen.favorite.FavoriteScreen
import com.dicoding.capstonecat.ui.screen.home.HomeScreen
import com.dicoding.capstonecat.ui.theme.CapstoneCatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { breed ->
                        navController.navigate(Screen.Detail.createRoute(breed))
                    }
                )
            }
            composable(Screen.Camera.route) {
                CameraScreen()
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = {
                        navController.navigate(Screen.Detail.createRoute(it))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("breed") { type = NavType.StringType })
            ) {
                val breed = it.arguments?.getString("breed") ?: ""
                val context = LocalContext.current
                DetailScreen(
                    breed = breed,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("breed") { type = NavType.StringType })
            ) {
                val breed = it.arguments?.getString("breed") ?: ""
                val context = LocalContext.current
                DetailScreen(
                    breed = breed,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = Modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                icon = Icons.Default.Home,
                screen = Screen.Home,
                contentDescription = stringResource(R.string.item_title_home)
            ),
            NavigationItem(
                icon = Icons.Default.AddCircle, //icon sementara, ganemu camera
                screen = Screen.Camera,
                contentDescription = stringResource(R.string.item_title_camera)
            ),
            NavigationItem(
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite,
                stringResource(R.string.item_title_favorite)
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(R.string.item_title_home)
                    )
                },
                selected = currentRoute == item.screen.route,
                onClick =  {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatAppPreview() {
    CapstoneCatTheme {
        CatApp()
    }
}