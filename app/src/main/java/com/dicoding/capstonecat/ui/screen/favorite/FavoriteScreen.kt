package com.dicoding.capstonecat.ui.screen.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.capstonecat.R
import com.dicoding.capstonecat.ViewModelFactory
import com.dicoding.capstonecat.data.entity.CatFavEntity
import com.dicoding.capstonecat.di.Injection
import com.dicoding.capstonecat.ui.common.UiState
import com.dicoding.capstonecat.ui.components.CatItem
import com.dicoding.capstonecat.ui.components.ScrollToTopButton
import com.dicoding.capstonecat.ui.theme.CapstoneCatTheme
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    modifier : Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit
) {
    viewModel.uiState.collectAsState(UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getFavCats()
            }
            is UiState.Success -> {
                FavoriteContent(
                    cat = it.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
            }
            else -> ""
        }
    }

}


@Composable
fun FavoriteContent(
    cat: List<CatFavEntity>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {

   if (cat.isNotEmpty()) {
       Box(
           modifier = modifier
       ) {
           val scope = rememberCoroutineScope()
           val gridState = rememberLazyGridState()
           val showButton: Boolean by remember {
               derivedStateOf { gridState.firstVisibleItemIndex > 2 }
           }

           LazyVerticalGrid(
               state = gridState,
               columns = GridCells.Adaptive(130.dp),
               contentPadding = PaddingValues(10.dp),
               horizontalArrangement = Arrangement.spacedBy(12.dp),
               verticalArrangement = Arrangement.spacedBy(10.dp),
               modifier = modifier
                   .fillMaxSize()
                   .padding(horizontal = 12.dp)
           ) {
               item(
                   span = { GridItemSpan(maxLineSpan) }
               ) {
                   Text(
                       text = stringResource(R.string.fav_prolog),
                       style = TextStyle(
                           fontSize = 24.sp,
                           lineHeight = 36.sp,
                           fontFamily = FontFamily(Font(R.font.mrexbold)),
                           fontWeight = FontWeight(800),
                       ),
                       modifier = Modifier
                           .padding(vertical = 10.dp)
                   )
               }
               items(cat.size) { index ->
                   CatItem(
                       imageUrl = cat[index].imgUrl!!,
                       name = cat[index].breed,
                       modifier = Modifier.clickable {
                           navigateToDetail(cat[index].breed)
                       }
                   )
               }
           }
           AnimatedVisibility(
               visible = showButton,
               enter = fadeIn() + slideInVertically(),
               exit = fadeOut() + slideOutVertically(),
               modifier = Modifier
                   .padding(bottom = 10.dp)
                   .align(Alignment.BottomCenter)
           ) {
               ScrollToTopButton(
                   onClick = {
                       scope.launch {
                           gridState.scrollToItem(index = 0)
                       }
                   }
               )
           }
       }
   } else {
       Box(
           contentAlignment = Alignment.Center,
           modifier = modifier.fillMaxSize()
       ) {
           Text(
               text = "Kucing Favorite Kosong",
               style = MaterialTheme.typography.titleMedium.copy(
                   fontWeight = FontWeight.Bold
               ),
               textAlign = TextAlign.Center,
           )
       }
   }
}

@Preview(showBackground = true)
@Composable
fun FavoriteContentPreview() {
    CapstoneCatTheme {
        //FavoriteContent(navigateToDetail = {})
    }
}

