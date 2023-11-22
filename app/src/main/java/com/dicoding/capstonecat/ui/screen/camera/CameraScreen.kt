package com.dicoding.capstonecat.ui.screen.camera

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier
) {
    CameraContent()
}

@Composable
fun CameraContent(

) {
    Text(text = "Camera")
}