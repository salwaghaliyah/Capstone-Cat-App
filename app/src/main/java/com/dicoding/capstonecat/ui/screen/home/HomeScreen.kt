package com.dicoding.capstonecat.ui.screen.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
//    viewModel: MainViewModel = viewModel(
//        factory = ViewModelFactory(Injection.provideRepository())
//    ),
    navigateToDetail: (Int) -> Unit,
) {
    HomeContent()
}


@Composable
fun HomeContent(

) {
    Text(text = "Homeee")
}