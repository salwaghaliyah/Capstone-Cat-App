package com.dicoding.capstonecat.ui.screen.favorite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoriteScreen(
    modifier : Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    FavoriteContent()
}


@Composable
fun FavoriteContent(

) {
    Text(text = "Favorite")
}